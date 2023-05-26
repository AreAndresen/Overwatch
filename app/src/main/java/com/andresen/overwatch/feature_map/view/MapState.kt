package com.andresen.overwatch.feature_map.view

import com.andresen.overwatch.feature_map.model.TargetUi
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapProperties

data class MapState(
    val lastKnownLocation: LatLng? = null,
    val properties: MapProperties = MapProperties(),
    val cameraPositionState: CameraPositionState = CameraPositionState(),
    val targets: List<TargetUi> = emptyList(),
    val friendlies: List<TargetUi> = emptyList(),
    val isNightVision: Boolean = false
)