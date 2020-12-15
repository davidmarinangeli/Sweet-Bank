package com.davidm

import com.davidm.ui.DashboardFragment
import com.davidm.ui.HomepageActivity
import org.koin.dsl.module

val dashboardModule = module {
    factory { HomepageActivity() }
    factory { DashboardFragment() }
}