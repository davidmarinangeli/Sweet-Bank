package com.davidm.payees

import com.davidm.payees.ui.PayeeCreationFragment
import com.davidm.payees.ui.PayeesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PayeesModule {

    @ContributesAndroidInjector
    abstract fun contributePayeeFragmentInjector(): PayeesFragment


    @ContributesAndroidInjector
    abstract fun contributePayeeCreationFragmentInjector(): PayeeCreationFragment


}