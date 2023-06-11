package com.andresen.libraryRepositories.units.remote

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnitApiService {

    @GET("photos")
    @Headers("Accept: application/json")
    suspend fun getUnits(): List<UnitDto>

    @GET("photos")
    @Headers("Accept: application/json")
    suspend fun getUnit(
        @Query("id") pageSize: Int,
    ): UnitDto

}