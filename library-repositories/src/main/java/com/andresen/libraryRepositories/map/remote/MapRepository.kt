package com.andresen.libraryRepositories.map.remote

import com.andresen.libraryRepositories.helper.network.DataResult


interface MapRepository {

    suspend fun insertMarker(marker: MarkerDto): DataResult<out Unit>

    suspend fun deleteMarker(marker: MarkerDto): DataResult<out Unit>

    suspend fun getMarkersDto(): DataResult<out MarkerWrapperDto>
}