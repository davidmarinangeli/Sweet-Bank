package com.davidm.account.repository

import com.davidm.account.entities.Accounts
import com.davidm.network.BASE_URL
import com.davidm.account.network.AccountApi
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit

@Module
class AccountRepositoryModule {

    @Provides
    fun providesAccountApi(retrofit: Retrofit.Builder): AccountApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(AccountApi::class.java)
    }
}