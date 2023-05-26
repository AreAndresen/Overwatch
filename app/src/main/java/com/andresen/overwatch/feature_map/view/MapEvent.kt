package com.andresen.overwatch.feature_map.view

import com.andresen.overwatch.feature_map.model.TargetUi
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {

    object ToggleNightVision: MapEvent()

    object LocateLastTarget: MapEvent()

    data class OnMapLongClick(val latLng: LatLng): MapEvent()

    data class CheckFriendlies(val latLng: LatLng): MapEvent()

    data class OnInfoBoxLongClick(val target: TargetUi): MapEvent()
}