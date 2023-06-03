package com.andresen.overwatch.feature_map.repository.data.remote.db

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface MapApiService {

    @GET
    suspend fun getMarkers(): MarkerWrapperDto

    @POST("/pushMarker/markers")
    suspend fun insertMarker(
        @Path("markerId") markerId: String,
        @Body markerRequestDto: MarkerDto
    )

    @DELETE
    suspend fun deleteMarker(
        @Url deleteLink: String
    )
}