package com.davidm.payees.entities

data class PayeeCreationResponse(
    val consentInformation: ConsentInformation?,
    val errors: List<ErrorMessage>?,
    val payeeUid: String?,
    val success: Boolean
)

data class ConsentInformation(
    val approvalType: String
)

data class ErrorMessage(
    val size: Int,
    val error: String,
    val error_description: String
)

val defaultError = ErrorMessage(10,"Generic error", "Generic error")