package com.davidm.account.repository

import com.davidm.account.network.AccountApi
import com.davidm.account.network.UserApi
import com.davidm.network.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class UserRepositoryModule {

    @Provides
    fun provideUserApi(retrofit: Retrofit.Builder): UserApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(UserApi::class.java)
    }
}