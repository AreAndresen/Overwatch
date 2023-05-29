package com.andresen.overwatch.main.helper.network

import android.content.Context
import com.andresen.overwatch.main.helper.network.ApiServiceFactory.Companion.moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType

class ApiServiceFactoryImpl(
    context: Context,
) {
    private val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

    val baseUrl = BASE_URL

    val okHttpClient = OkHttpClient
        .Builder()
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val moshiConverterFactory = MoshiConverterFactory.create(moshi)
    inline fun <reified T : Any> createService(): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .addConverterFactory(moshiConverterFactory)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .addConverterFactory(MoshiConverterFactory.create(ApiServiceFactory.moshi))
            //.addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .client(okHttpClient)
            .build()
            .create(T::class.java)
    }


}