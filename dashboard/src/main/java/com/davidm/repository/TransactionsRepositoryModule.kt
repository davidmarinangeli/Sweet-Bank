package com.davidm.repository

import com.davidm.network.BASE_URL
import com.davidm.network.TransactionsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TransactionsRepositoryModule {

    @Provides
    fun providesTransactionsApi(retrofit: Retrofit.Builder): TransactionsApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(TransactionsApi::class.java)
    }
}