package com.davidm.ui

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.davidm.ui.databinding.FragmentDashboardBinding
import com.davidm.utils.DateIntervalHelper
import com.davidm.utils.ImageUtils.Companion.getRealPathFromURI
import com.davidm.utils.Permission
import com.davidm.utils.PermissionManager
import org.koin.android.ext.android.inject
import java.io.File
import java.text.DateFormatSymbols
import java.util.*


class DashboardFragment : Fragment() {

    val viewModel by inject<DashboardViewModel>()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var takePicture: ActivityResultLauncher<Void>
    lateinit var parentListAdapter: DashboardParentListAdapter
    lateinit var nestedListAdapter: DashboardNestedListAdapter
    lateinit var calendar: Calendar
    private val permissionManager = PermissionManager.from(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        nestedListAdapter = DashboardNestedListAdapter()

        viewModel.accountBalanceLiveData.observe(viewLifecycleOwner, {
            binding.balanceAmountMain.text = it.amount
            binding.balanceAmountCents.text = it.amountCents
        })

        takePicture =
            registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap -> // Handle the image as a Bitmap
                val finalFile = File(getRealPathFromURI(requireContext(), bitmap))
                viewModel.uploadProfilePicture(bitmap, finalFile)

                viewModel.profilePictureLiveData.observe(viewLifecycleOwner, {
                    if (it != null) {
                        binding.profilePicture.imageTintMode = null
                        binding.profilePicture.setImageBitmap(
                            it
                        )
                    }
                })
            }


        val data: List<DashboardParentListAdapter.ParentListItem> =
            DateIntervalHelper().generateDateIntervalList().map {
                DashboardParentListAdapter.ParentListItem(it.month, nestedListAdapter)
            }

        parentListAdapter = DashboardParentListAdapter(data, requireContext(), true)

        // starting point with the viewpager: we set the month to the current one and fetch the list
        binding.parentListViewPager.apply {
            adapter = parentListAdapter
            isUserInputEnabled = false
            currentItem = calendar.get(Calendar.MONTH) + 1
        }
        binding.transactionsMonthTitle.text =
            DateFormatSymbols().months[binding.parentListViewPager.currentItem]
        viewModel.getPurchases(DateIntervalHelper().generateDateIntervalList()[binding.parentListViewPager.currentItem])


        viewModel.userLiveData.observe(viewLifecycleOwner, {
            binding.helloUserTitle.text = getString(
                R.string.hello_user_text,
                it.firstName
            )
        })
        viewModel.getUserInfo()

        binding.profilePicture.setOnClickListener {
            permissionManager
                .request(Permission.Camera)
                .rationale("We need permission to use the camera")
                .checkPermission { granted: Boolean ->
                    if (granted) {
                        takePicture.launch(null)
                    }
                }

        }

        viewModel.fetchErrorLiveData.observe(viewLifecycleOwner, {
            parentListAdapter.error = it
            parentListAdapter.loading = false
            parentListAdapter.notifyDataSetChanged()
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getPurchases(DateIntervalHelper().generateDateIntervalList()[binding.parentListViewPager.currentItem])
            binding.swipeRefresh.isRefreshing = false
        }

        binding.previousMonthButton.setOnClickListener {
            binding.parentListViewPager.setCurrentItem(
                binding.parentListViewPager.currentItem - 1,
                true
            )
        }

        binding.nextMonthButton.setOnClickListener {
            binding.parentListViewPager.setCurrentItem(
                binding.parentListViewPager.currentItem + 1,
                true
            )
        }

        binding.parentListViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                binding.transactionsMonthTitle.text = DateFormatSymbols().months[position]
                viewModel.getPurchases(DateIntervalHelper().generateDateIntervalList()[position])
                parentListAdapter.loading = true
                parentListAdapter.notifyDataSetChanged()
            }
        })

        viewModel.purchasesLiveData.observe(viewLifecycleOwner, {
            nestedListAdapter.data = it
            parentListAdapter.data[binding.parentListViewPager.currentItem].listAdapter =
                nestedListAdapter
            parentListAdapter.loading = false
            parentListAdapter.notifyDataSetChanged()

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
