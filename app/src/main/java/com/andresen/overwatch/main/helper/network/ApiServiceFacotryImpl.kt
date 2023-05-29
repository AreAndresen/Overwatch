package com.andresen.overwatch.main.helper.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceFactoryImpl(
    context: Context,
) {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    val baseUrl = BASE_URL

    val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    inline fun <reified T : Any> createService(): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(ApiServiceFactory.moshi))
            .client(okHttpClient)
            .build()
            .create(T::class.java)
    }


}