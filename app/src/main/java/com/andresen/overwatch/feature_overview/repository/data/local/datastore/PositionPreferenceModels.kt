package com.andresen.overwatch.feature_overview.repository.data.local.datastore

import androidx.datastore.preferences.core.doublePreferencesKey

object PositionPreferenceIds {
    val LAST_POSITION_LAT = doublePreferencesKey("LAST_POSITION_LAT")
    val LAST_POSITION_LNG = doublePreferencesKey("LAST_POSITION_LNG")
}

