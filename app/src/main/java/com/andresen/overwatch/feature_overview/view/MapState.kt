package com.andresen.feature_overview.view

import com.google.maps.android.compose.MapProperties

data class MapState (
    val properties: MapProperties = MapProperties(),
    val isNightVision: Boolean = false
)