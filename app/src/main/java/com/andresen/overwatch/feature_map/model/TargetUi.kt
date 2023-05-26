package com.andresen.overwatch.feature_map.model

import com.andresen.overwatch.feature_map.repository.data.remote.db.FriendlyTargetDto

data class TargetUi(
    val id: Int? = null,
    val friendly: Boolean = false,
    val lat: Double,
    val lng: Double,
)

/*
data class Friendlies(
    val friendlies: List<TargetUi>
) */

sealed interface MapUiState {
    data class Success(val targets: List<TargetUi>) : MapUiState
    object Error : MapUiState
    object Loading : MapUiState
}