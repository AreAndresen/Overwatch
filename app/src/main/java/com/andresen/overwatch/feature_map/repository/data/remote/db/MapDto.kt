package com.andresen.overwatch.feature_map.repository.data.remote.db



data class MarkerWrapperDto(
     val markersDto: List<MarkerDto>
)

data class MarkerDto(
    val id: Int? = null,
    val friendly: Boolean = true,
    val lat: Double,
    val lng: Double
)

