package com.andresen.overwatch.helper.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber


interface ApiServiceFactory {

    companion object {
        val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory()) // Most generic adapter last
            .build()
    }
}


internal inline fun <reified T> Moshi.convertToJson(instance: T): String? = try {
    adapter(T::class.java).toJson(instance)
} catch (ex: Exception) {
    Timber.e(ex, "Failed converting ${T::class.simpleName} to json")
    null
}

internal inline fun <reified T> Moshi.convertFromJson(blob: String?): T? = blob?.let {
    try {
        adapter(T::class.java).fromJson(it)
    } catch (ex: Exception) {
        Timber.e(ex, "Failed converting ${T::class.simpleName} from json")
        null
    }
}
