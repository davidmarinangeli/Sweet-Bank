package com.davidm.payees.utils

interface CustomDispatcher {
    fun getStandardDispatcher()
    fun getIODispatcher()
}