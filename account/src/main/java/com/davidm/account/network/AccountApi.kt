package com.davidm.account.network

import com.davidm.account.entities.AccountBalance
import com.davidm.account.entities.AccountHolder
import com.davidm.account.entities.Accounts
import com.davidm.network.API_KEY
import com.davidm.network.API_VERSION
import okhttp3.RequestBody
import retrofit2.http.*

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


    @GET("api/$API_VERSION/account-holder")
    suspend fun getAccountHolder(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): AccountHolder

    @GET("api/$API_VERSION/account-holder/{accountHolder}/profile-image")
    suspend fun getAccountPicture(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json",
        @Path("accountHolder") accountHolder: String,
    ): String

    @PUT("api/$API_VERSION/account-holder/{accountHolder}/profile-image")
    suspend fun uploadAccountPicture(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Content-type") value: String = "image/jpeg",
        @Path("accountHolder") accountHolder: String,
        @Body requestBody: RequestBody
    ): Array<String>?
}