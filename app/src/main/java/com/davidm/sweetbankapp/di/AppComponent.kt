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
import com.davidm.account.repository.UserRepositoryModule
import com.davidm.payees.PayeesModule
import com.davidm.payees.repository.PayeesRepositoryModule
import com.davidm.repository.TransactionsRepositoryModule
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
        TransactionsRepositoryModule::class,
        PayeesRepositoryModule::class,
        UserRepositoryModule::class
    ]
)
interface AppComponent : AndroidInjector<SweetBank> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }
}