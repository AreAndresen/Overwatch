package com.andresen.overwatch.helper.network

import kotlinx.coroutines.withTimeout
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException
import timber.log.Timber

class RequestHelper(private val connectionService: ConnectionService) {
    suspend fun <T> tryRequest(
        timeout: Long? = null,
        fallback: (suspend () -> T)? = null,
        request: suspend () -> T
    ): DataResult<out T> = try {
        if (timeout != null) {
            withTimeout(timeout) { DataResult.Success.NetworkSuccess(request()) }
        } else {
            DataResult.Success.NetworkSuccess(request())
        }
    } catch (err: Exception) {
        val fallbackResult = try {
            val data = fallback!!()
            DataResult.Success.OfflineSuccess(data)
        } catch (err: Exception) {
            null
        }

        if (fallbackResult != null) {
            Timber.i("Request failed, returning fallback instead")
            fallbackResult
        } else {
            when (err) {
                is HttpException,
                is ConnectionShutdownException -> DataResult.Error.AppError(err)

                else -> if (!connectionService.isConnectedToInternet()) {
                    DataResult.Error.NoNetwork(err)
                } else DataResult.Error.AppError(err)
            }.also {
                if (it is DataResult.Error.AppError) {
                    Timber.w(err, "Request failed")
                } else {
                    Timber.w("Request failed, client has no network (${err::class.simpleName})")
                }
            }
        }
    }
}