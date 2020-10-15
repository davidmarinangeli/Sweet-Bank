package com.davidm.sweetbankapp

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import com.davidm.sweetbankapp.di.AppComponent
import com.davidm.sweetbankapp.di.DaggerAppComponent
import javax.inject.Inject

class SweetBank : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.factory()
            .create(this)
        component.inject(this)
    }
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    companion object {
        lateinit var component: AppComponent
    }
}