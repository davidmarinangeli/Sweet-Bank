package com.davidm.payees.repository

import com.davidm.network.BASE_URL
import com.davidm.payees.network.PayeesApi
import retrofit2.Retrofit

class PayeesRepositoryModule {


    fun providesPayeesApi(retrofit: Retrofit.Builder): PayeesApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(PayeesApi::class.java)
    }
}