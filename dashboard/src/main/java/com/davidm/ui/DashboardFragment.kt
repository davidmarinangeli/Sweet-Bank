package com.davidm.ui

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.davidm.ui.databinding.FragmentDashboardBinding
import com.davidm.utils.DateIntervalHelper
import com.davidm.utils.ImageUtils.Companion.getRealPathFromURI
import org.koin.android.ext.android.inject
import java.io.File
import java.text.DateFormatSymbols
import java.util.Calendar


class DashboardFragment : Fragment() {

    val viewModel by inject<DashboardViewModel>()

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    lateinit var parentListAdapter: DashboardParentListAdapter
    lateinit var nestedListAdapter: DashboardNestedListAdapter
    lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = Calendar.getInstance()

        viewModel.accountBalanceLiveData.observe(viewLifecycleOwner, {
            binding.balanceAmountMain.text = it.amount
            binding.balanceAmountCents.text = it.amountCents
        })

        nestedListAdapter = DashboardNestedListAdapter()

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
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.CAMERA),
                    0
                )
            } else {
                openCameraIntent()
            }
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

    private fun openCameraIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(
            takePictureIntent,
            0
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "camera permission granted", Toast.LENGTH_LONG)
                    .show()
                openCameraIntent()
            } else {
                Toast.makeText(requireContext(), "camera permission denied", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val selectedImage = data?.extras?.get("data") as Bitmap
            val finalFile = File(getRealPathFromURI(requireContext(), selectedImage))

            viewModel.uploadProfilePicture(selectedImage, finalFile)

            viewModel.profilePictureLiveData.observe(viewLifecycleOwner, {
                if (it == null) {
                    binding.profilePicture.imageTintMode = null
                    binding.profilePicture.setImageBitmap(
                        it
                    )
                }
            })

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
