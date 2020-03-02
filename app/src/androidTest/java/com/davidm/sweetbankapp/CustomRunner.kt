package com.davidm.sweetbankapp

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class CustomRunner : AndroidJUnitRunner() {
    @Throws(Exception::class)
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(
            cl,
            SweetBank::class.java.name,
            context
        )
    }
}