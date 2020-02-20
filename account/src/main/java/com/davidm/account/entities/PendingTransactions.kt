package com.davidm.account.entities

data class PendingTransactions(
    val currency: String,
    val minorUnits: Int
)