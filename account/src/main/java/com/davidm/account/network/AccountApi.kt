package com.davidm.account.network

import com.davidm.account.entities.AccountBalance
import com.davidm.account.entities.Accounts
import com.davidm.network.API_KEY
import com.davidm.network.API_VERSION
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AccountApi {

    @GET("api/$API_VERSION/accounts")
    suspend fun getAccounts(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): Accounts

    @GET("api/$API_VERSION/accounts/{accountId}/balance")
    suspend fun getAccountBalance(
        @Path("accountId") accountId: String,
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): AccountBalance
}