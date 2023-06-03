package com.andresen.overwatch.feature_map

import com.andresen.overwatch.feature_map.model.MarkerUi
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {

    object ToggleNightVision: MapEvent()

    data class UpdateZoomLocation(val latLng: LatLng): MapEvent()

    data class CreateMarkerLongClick(val latLng: LatLng): MapEvent()

    data class UpdateMarkers(val latLng: LatLng): MapEvent()

    data class OnInfoBoxLongClick(val marker: MarkerUi): MapEvent()
}