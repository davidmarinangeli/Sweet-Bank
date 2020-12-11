package com.davidm.account.network

import com.davidm.account.entities.AccountBalance
import com.davidm.account.entities.Accounts
import com.davidm.account.entities.User
import com.davidm.network.API_KEY
import com.davidm.network.API_VERSION
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {

    @GET("api/$API_VERSION/identity/individual")
    suspend fun getUser(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): User
}