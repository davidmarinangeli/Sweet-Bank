package com.davidm.payees.entities

import java.util.*

data class PayeeAccount(
    val accountIdentifier: String,
    val bankIdentifier: String,
    val bankIdentifierType: String?,
    val countryCode: String?,
    val defaultAccount: Boolean?,
    val description: String?,
    val payeeAccountUid: String?
)

val defaultAccount: PayeeAccount = PayeeAccount(
    accountIdentifier = "00000825",
    bankIdentifier = "204514",
    bankIdentifierType = "SORT_CODE",
    countryCode = "GB",
    defaultAccount = true,
    description = "Another account bites the dust",
    payeeAccountUid = UUID.randomUUID().toString()
)