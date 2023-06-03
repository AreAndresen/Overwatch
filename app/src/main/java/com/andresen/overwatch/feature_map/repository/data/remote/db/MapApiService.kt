package com.andresen.overwatch.feature_map.repository.data.remote.db

import retrofit2.http.GET

interface MapApiService {

    /*@GET("/todos")
    suspend fun getTodos(): Response<List<Todo>>*/

    @GET //("/radio/userdata/{userId}/friendlies")
    suspend fun getFriendlies(
        //@Path("userId") userId: String,
        //@Query("friendlies") friendlies: String?,
    ): MarkerWrapperDto
}