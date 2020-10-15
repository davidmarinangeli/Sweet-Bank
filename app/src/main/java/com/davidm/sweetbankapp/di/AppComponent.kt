package com.davidm.sweetbankapp.di

import android.app.Application
import com.davidm.DashboardModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import com.davidm.network.di.BaseNetworkModule
import com.davidm.sweetbankapp.SweetBank
import com.davidm.account.repository.AccountRepositoryModule
import com.davidm.payees.PayeesModule
import com.davidm.payees.repository.PayeesRepositoryModule
import com.davidm.repository.DashboardRepositoryModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        BaseNetworkModule::class,
        DashboardModule::class,
        PayeesModule::class,
        ViewModelModule::class,
        AccountRepositoryModule::class,
        DashboardRepositoryModule::class,
        PayeesRepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<SweetBank> {

    fun inject(david: SweetBank.David)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}