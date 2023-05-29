package com.andresen.overwatch.main.helper.network

import retrofit2.HttpException
import timber.log.Timber

sealed class DataResult<T> {
    sealed class Error : DataResult<Nothing>() {
        abstract val err: Throwable?
        val httpStatusCode: Int? get() = (err as? HttpException)?.code()

        inline fun <reified R> getErrorResponse(): R? {
            val response = (err as? HttpException)?.response() ?: return null
            val adapter = ApiServiceFactory.moshi.adapter(R::class.java).lenient()
            val rawErrorBody = response.errorBody()?.source()
            return if (rawErrorBody != null) {
                try {
                    adapter.fromJson(rawErrorBody)
                } catch (err: Exception) {
                    Timber.w(err, "Failed parsing error body: $rawErrorBody")
                    null
                }
            } else {
                null
            }
        }

        data class NoNetwork(override val err: Throwable?) : Error()
        data class AppError(override val err: Throwable?) : Error()
    }

    sealed class Success<T> : DataResult<T>() {
        abstract val data: T

        data class NetworkSuccess<T>(override val data: T) : Success<T>()
        data class OfflineSuccess<T>(override val data: T) : Success<T>()
        data class CachedSuccess<T>(override val data: T) : Success<T>()
    }

    fun getOrNull(): T? = (this as? Success)?.data
}