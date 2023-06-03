package com.andresen.overwatch.feature_map.repository.data.remote.db

import com.andresen.overwatch.main.helper.network.DataResult

interface MapRepository {

    suspend fun insertMarker(marker: MarkerDto): DataResult<out Unit>

    suspend fun deleteMarker(marker: MarkerDto): DataResult<out Unit>

    suspend fun getMarkersDto(): DataResult<out MarkerWrapperDto>
}