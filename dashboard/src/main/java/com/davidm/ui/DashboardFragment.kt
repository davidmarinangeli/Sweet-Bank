package com.davidm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.davidm.ui.databinding.FragmentDashboardBinding
import com.davidm.utils.DateIntervalHelper
import dagger.android.support.AndroidSupportInjection
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject

class DashboardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var vm: DashboardViewModel

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DashboardViewModel
    lateinit var parentListAdapter: DashboardParentListAdapter
    lateinit var nestedListAdapter: DashboardNestedListAdapter
    lateinit var calendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

    }

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
        viewModel =
            ViewModelProvider(
                requireActivity().viewModelStore,
                viewModelFactory
            ).get(DashboardViewModel::class.java)

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
        binding.parentListViewPager.adapter = parentListAdapter
        binding.parentListViewPager.isUserInputEnabled = false


        viewModel.userLiveData.observe(viewLifecycleOwner, {
            binding.helloUserTitle.text = getString(
                R.string.hello_user_text,
                it.firstName
            )
        })
        viewModel.getUserInfo()

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
