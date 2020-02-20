package com.davidm.payees.entities

import com.squareup.moshi.Json

data class Payee(
    val accounts: List<PayeeAccount>,
    val businessName: String?,
    val dateOfBirth: String?,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val payeeName: String,
    val payeeType: PayeeType?,
    val payeeUid: String,
    val phoneNumber: String?
)

data class Payees(
    val payees: List<Payee>
)

enum class PayeeType {
    @Json(name = "BUSINESS") BUSINESS,
    @Json(name = "INDIVIDUAL") INDIVIDUAL,
}