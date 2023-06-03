package com.andresen.overwatch.feature_map.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

data class MapUi(
    val mapTopAppBar: MapTopAppBar,
    val mapContent: MapContentUi
)

sealed interface MapContentUi {
    object Loading : MapContentUi
    object Error : MapContentUi

    data class MapContent(
        val userLocation: LatLng? = null,
        val zoomLocation: LatLng,
        val properties: MapProperties = MapProperties(),
        val markers: List<MarkerUi> = emptyList()
    ) : MapContentUi
}


data class MarkerUi(
    val id: Int? = null,
    val friendly: Boolean = false,
    val lat: Double,
    val lng: Double,
)


data class MapTopAppBar(
    val isNightVision: Boolean = false
)