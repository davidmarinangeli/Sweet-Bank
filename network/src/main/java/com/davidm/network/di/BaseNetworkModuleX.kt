package com.davidm.network.di

import com.davidm.network.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val baseNetworkModule = module {

    factory<okhttp3.Interceptor> {
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).setLevel(
            HttpLoggingInterceptor.Level.BODY
        )
    }

    factory {
        OkHttpClient.Builder().addInterceptor(interceptor = get()).build()
    }

    factory<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    factory<Retrofit.Builder> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
    }
}