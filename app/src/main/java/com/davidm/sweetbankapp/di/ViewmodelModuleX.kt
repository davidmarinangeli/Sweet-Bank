package com.davidm.sweetbankapp.di

import com.davidm.ui.DashboardViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        DashboardViewModel(get(), get(), get())
    }
}