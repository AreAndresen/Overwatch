package com.andresen.overwatch.feature_units.repository.remote.db

import retrofit2.http.GET
import retrofit2.http.Headers

interface UnitApiService {

    @GET("photos")
    @Headers("Accept: application/json")
    suspend fun getUnits(): List<MarsPhoto> //UnitsWrapperDto


    /*@GET //("/radio/userdata/{userId}/friendlies")
    suspend fun getUnits(
        //@Path("userId") userId: String,
        //@Query("friendlies") friendlies: String?,
    ): UnitsWrapperDto*/
}