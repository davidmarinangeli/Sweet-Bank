package com.davidm.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.davidm.payees.ui.PayeesFragment

class HomepageAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf<Fragment>(
        DashboardFragment(),
        PayeesFragment()
    )

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return fragmentList[0]
            1 -> {
                return fragmentList[1]
            }
        }
        return DashboardFragment()
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }
}