package com.andresen.overwatch.feature_overview.view

import com.andresen.overwatch.feature_overview.model.TargetUi
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {
    object ToggleNightVision: MapEvent()

    data class OnMapLongClick(val latLng: LatLng): MapEvent()

    data class OnInfoBoxLongClick(val target: TargetUi): MapEvent()
}