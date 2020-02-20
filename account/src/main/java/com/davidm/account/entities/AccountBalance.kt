package com.davidm.account.entities

data class AccountBalance(
    val acceptedOverdraft: AcceptedOverdraft,
    val amount: Amount,
    val clearedBalance: ClearedBalance,
    val effectiveBalance: EffectiveBalance,
    val pendingTransactions: PendingTransactions
)