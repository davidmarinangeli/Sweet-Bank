package com.davidm.payees.network

import com.davidm.account.entities.Account
import com.davidm.network.API_KEY
import com.davidm.network.API_VERSION
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeCreationResponse
import com.davidm.payees.entities.Payees
import retrofit2.http.*
import java.time.Instant

interface PayeesApi {

    @GET("api/$API_VERSION/payees")
    suspend fun getPayees(
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): Payees


    @PUT("api/${API_VERSION}/payees")
    suspend fun createPayee(
        @Body body: Payee,
        @Header("Authorization") token: String = "Bearer $API_KEY",
        @Header("Accept") value: String = "application/json"
    ): PayeeCreationResponse
}