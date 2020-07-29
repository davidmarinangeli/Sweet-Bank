package com.davidm.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.davidm.utils.DateIntervalHelper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.text.DateFormatSymbols
import java.util.*
import javax.inject.Inject

class DashboardFragment : Fragment(), MotionLayout.TransitionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = view.findViewById<ViewPager2>(R.id.parent_list)
        val balanceCardTextView = view.findViewById<TextView>(R.id.balance_amount)
        val balanceCentsTextView = view.findViewById<TextView>(R.id.cents)

        motion_layout.setTransitionListener(this)

        calendar = Calendar.getInstance()
        viewModel =
            ViewModelProvider(
                activity!!.viewModelStore,
                viewModelFactory
            ).get(DashboardViewModel::class.java)

        viewModel.accountBalanceLiveData.observe(viewLifecycleOwner, Observer {
            balanceCardTextView.text = it.amount
            balanceCentsTextView.text = it.amountCents
        })

        nestedListAdapter = DashboardNestedListAdapter()

        val data: List<DashboardParentListAdapter.ParentListItem> =
            DateIntervalHelper().generateDateIntervalList().map {
                DashboardParentListAdapter.ParentListItem(it.month, nestedListAdapter)
            }

        parentListAdapter = DashboardParentListAdapter(data, context!!, true)
        viewPager.adapter = parentListAdapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {

                date_interval_text.text = DateFormatSymbols().months[position]

                viewModel.getPurchases(DateIntervalHelper().generateDateIntervalList()[position])
                parentListAdapter.loading = true
                parentListAdapter.notifyDataSetChanged()
            }
        })

        viewModel.purchasesLiveData.observe(viewLifecycleOwner, Observer {
            nestedListAdapter.data = it
            parentListAdapter.data[viewPager.currentItem].listAdapter = nestedListAdapter
            parentListAdapter.loading = false
            parentListAdapter.notifyDataSetChanged()

        })

    }

    override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
    }

    override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
    }

    override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
    }

    override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
        (requireActivity() as HomepageActivity).switchBottomAppBarVisibility()
    }


}
