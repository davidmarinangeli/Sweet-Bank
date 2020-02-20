package com.davidm.repository

import com.davidm.network.BASE_URL
import com.davidm.network.DashboardApi
import com.davidm.account.network.AccountApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class DashboardRepositoryModule {

    @Provides
    fun providesDashboardApi(retrofit: Retrofit.Builder): DashboardApi {
        return retrofit
            .baseUrl(BASE_URL)
            .build()
            .create(DashboardApi::class.java)
    }
}