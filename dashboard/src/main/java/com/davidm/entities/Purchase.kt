package com.davidm.entities

import com.davidm.account.entities.Amount
import com.squareup.moshi.Json
import java.util.*

data class Purchases(
    val feedItems: List<Purchase>
)

data class Purchase(

    val feedItemUid: String,
    val categoryUid: String,
    val amount: Amount,
    // val sourceAmount: SourceAmount,
    // val direction: String,
    // val updatedAt: String,
    val transactionTime: String,
    //val settlementTime: String?,
    // val source: String,
    //val sourceSubType: String?,
    //val status: String,
    // val counterPartyType: String,
    // val counterPartyUid: String,
    val counterPartyName: String,
    //val counterPartySubEntityUid: String,
    //val reference: String,
    //val country: String,
    val spendingCategory: SpendingCategory
)

enum class SpendingCategory {
    @Json(name = "PAYMENTS") PAYMENTS,
    @Json(name = "EATING_OUT") EATING_OUT,
    @Json(name = "INCOME") INCOME,
    @Json(name = "GENERAL") GENERAL
}
