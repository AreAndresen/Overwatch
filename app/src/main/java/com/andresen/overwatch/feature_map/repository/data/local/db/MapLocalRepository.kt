package com.andresen.overwatch.feature_map.repository.data.local.db

import com.andresen.overwatch.feature_map.model.MarkerUi
import kotlinx.coroutines.flow.Flow

interface MapLocalRepository {

    suspend fun insertMarker(marker: MarkerUi)

    suspend fun deleteMarker(marker: MarkerUi)

    fun getMarkers(): Flow<List<MarkerUi>>
}