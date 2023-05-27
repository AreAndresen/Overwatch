package com.andresen.overwatch.feature_map.repository.data.remote.db



data class FriendlyTargetWrapperDto(
     val friendlies: List<FriendlyTargetDto>
)

data class FriendlyTargetDto(
    val id: Int? = null,
    val friendly: Boolean = true,
    val lat: Double,
    val lng: Double,
)

