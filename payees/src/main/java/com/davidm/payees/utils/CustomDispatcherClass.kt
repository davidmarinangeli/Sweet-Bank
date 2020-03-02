package com.davidm.payees.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CustomDispatcherClass : CustomDispatcher {

    var IO: CoroutineDispatcher = Dispatchers.IO
    var Default: CoroutineDispatcher = Dispatchers.Default

    override fun getStandardDispatcher() {
        Default = Dispatchers.Default
    }

    override fun getIODispatcher() {
        IO = Dispatchers.IO
    }
}