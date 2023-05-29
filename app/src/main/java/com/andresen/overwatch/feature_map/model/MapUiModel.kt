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
        val isNightVision: Boolean = false,
        val userLocation: LatLng? = null,
        val zoomLocation: LatLng,
        val properties: MapProperties = MapProperties(),
        val targets: List<TargetUi> = emptyList(),
        val friendlies: List<TargetUi> = emptyList(),
    ) : MapContentUi
}


data class TargetUi(
    val id: Int? = null,
    val friendly: Boolean = false,
    val lat: Double,
    val lng: Double,
)


data class MapTopAppBar(
    val isNightVision: Boolean = false
)