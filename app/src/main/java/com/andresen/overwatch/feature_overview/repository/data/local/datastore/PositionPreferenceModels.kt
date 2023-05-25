package com.andresen.overwatch.feature_overview.repository.data.local.datastore

import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PositionPreferenceIds {

    val LAST_POSITION_LAT_LNG = stringPreferencesKey("LAST_POSITION_LAT_LNG")

    val LAST_POSITION_LAT = doublePreferencesKey("LAST_POSITION_LAT")
    val LAST_POSITION_LNG = doublePreferencesKey("LAST_POSITION_LNG")
}


sealed class PositonLatLng() {

}


sealed class LatLngData(
    val lat: Double,
    val lng: Double,
)