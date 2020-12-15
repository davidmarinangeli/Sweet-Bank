package com.davidm.account.repository

import com.davidm.account.network.AccountApi
import org.koin.dsl.module
import retrofit2.Retrofit

val accountRepositoryModule = module {

    single {
        AccountRepository(get())
    }

    single {
        (get() as Retrofit.Builder).build()
            .create(AccountApi::class.java)
    }
}