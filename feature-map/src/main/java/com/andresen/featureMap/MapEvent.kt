package com.andresen.featureMap

import com.andresen.featureMap.model.MarkerUi
import com.google.android.gms.maps.model.LatLng

sealed class MapEvent {

    object ToggleNightVision : MapEvent()

    data class UpdateZoomLocation(val latLng: LatLng) : MapEvent()

    data class CreateMarker(val latLng: LatLng) : MapEvent()

    data class UpdateMarkers(val latLng: LatLng) : MapEvent()

    data class DeleteMarker(val marker: MarkerUi) : MapEvent()
}