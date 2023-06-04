package com.andresen.libraryRepositories.map.local.db

import kotlinx.coroutines.flow.Flow

interface MapLocalRepository {

    suspend fun insertMarker(marker: MarkerEntity)

    suspend fun deleteMarker(marker: MarkerEntity)

    fun getMarkers(): Flow<List<MarkerEntity>>
}