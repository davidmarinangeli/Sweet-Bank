package com.davidm.payees.entities

data class PayeeAccount(
    val accountIdentifier: String,
    val bankIdentifier: String,
    val bankIdentifierType: String?,
    val countryCode: String?,
    val defaultAccount: Boolean?,
    val description: String?,
    val payeeAccountUid: String?
)