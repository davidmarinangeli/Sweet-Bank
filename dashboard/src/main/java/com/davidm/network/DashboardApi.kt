package com.davidm.network

import com.davidm.entities.Purchases
import retrofit2.http.*
import java.time.Instant

interface DashboardApi {

    @GET("api/$API_VERSION/feed/account/{account}/category/{category}/transactions-between")
    suspend fun getPurchasesInAWeek(
        @Path("account") account: String,
        @Path("category") category: String,
        @Query("minTransactionTimestamp") minTime: String,
        @Query("maxTransactionTimestamp") maxTime: String,
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): Purchases
}