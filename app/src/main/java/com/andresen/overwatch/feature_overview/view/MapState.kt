package com.andresen.overwatch.feature_overview.view

import com.andresen.overwatch.feature_overview.model.TargetUi
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties

data class MapState (
    val properties: MapProperties = MapProperties(),
    val cameraPositionState: CameraPositionState = CameraPositionState(),
    val targets: List<TargetUi> = emptyList(),
    val isNightVision: Boolean = false
)