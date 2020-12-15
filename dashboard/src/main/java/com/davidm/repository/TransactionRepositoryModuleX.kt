package com.davidm.repository

import com.davidm.network.TransactionsApi
import org.koin.dsl.module
import retrofit2.Retrofit

val transactionsRepositoryModule = module {

    single {
        TransactionsRepository(get())
    }

    single<TransactionsApi> {
        (get() as Retrofit.Builder).build()
            .create(TransactionsApi::class.java)
    }
}