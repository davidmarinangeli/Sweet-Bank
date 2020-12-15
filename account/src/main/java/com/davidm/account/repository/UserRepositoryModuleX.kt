package com.davidm.account.repository

import com.davidm.account.network.UserApi
import org.koin.dsl.module
import retrofit2.Retrofit

val userRepositoryModule = module {

    single {
        UserRepository(get())
    }

    single<UserApi> {
        (get() as Retrofit.Builder).build()
            .create(UserApi::class.java)
    }
}