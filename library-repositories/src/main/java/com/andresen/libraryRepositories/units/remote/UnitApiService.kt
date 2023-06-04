package com.andresen.libraryRepositories.units.remote

import retrofit2.http.GET
import retrofit2.http.Headers

interface UnitApiService {

    @GET("photos")
    @Headers("Accept: application/json")
    suspend fun getUnits(): List<UnitDto>

}