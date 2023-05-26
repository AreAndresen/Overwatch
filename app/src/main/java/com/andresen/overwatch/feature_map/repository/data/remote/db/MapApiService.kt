package com.andresen.overwatch.feature_map.repository.data.remote.db

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapApiService {

    /*@GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>*/

    @GET //("/radio/userdata/{userId}/newepisodes/count")
    suspend fun getFriendlies(
        //@Path("userId") userId: String,
        //@Query("friendly") since: String?,
    ): FriendlyTargetWrapperDto
}