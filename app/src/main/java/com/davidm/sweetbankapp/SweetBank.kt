package com.davidm.sweetbankapp

import android.app.Application
import com.davidm.account.repository.accountRepositoryModule
import com.davidm.account.repository.userRepositoryModule
import com.davidm.dashboardModule
import com.davidm.network.di.baseNetworkModule
import com.davidm.repository.transactionsRepositoryModule
import com.davidm.sweetbankapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SweetBank : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SweetBank)
            modules(
                dashboardModule, viewModelModule, userRepositoryModule, baseNetworkModule,
                accountRepositoryModule, transactionsRepositoryModule
            )
        }
    }
}