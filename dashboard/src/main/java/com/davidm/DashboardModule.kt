package com.davidm

import com.davidm.payees.ui.PayeesFragment
import com.davidm.ui.HomepageActivity
import com.davidm.ui.DashboardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DashboardModule {

    @ContributesAndroidInjector
    abstract fun contributeDashboardInjector(): HomepageActivity

    @ContributesAndroidInjector
    abstract fun contributeDashboardFragmentInjector(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributePayeeFragmentInjector(): PayeesFragment

}