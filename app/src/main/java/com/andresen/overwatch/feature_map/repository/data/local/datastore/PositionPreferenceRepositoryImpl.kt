package com.andresen.overwatch.feature_map.repository.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.andresen.overwatch.helper.OverwatchDispatchers
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val Context.positionDataStore by preferencesDataStore(name = "position")

class PositionPreferenceRepositoryImpl(
    context: Context,
    private val dispatchers: OverwatchDispatchers,
) : PositionPreferenceRepository {

    private val dataStore = context.positionDataStore


    override val lastPositionLatFlow = dataStore.data.map { preference ->
        preference[PositionPreferenceIds.LAST_POSITION_LAT] ?: 0.0
    }

    override val lastPositionLngFlow = dataStore.data.map { preference ->
        preference[PositionPreferenceIds.LAST_POSITION_LNG] ?: 0.0
    }

    override suspend fun setLastPositionLatLng(latLng: LatLng) {
        withContext(dispatchers.io) {
            dataStore.edit { preference ->
                preference[PositionPreferenceIds.LAST_POSITION_LAT] = latLng.latitude
                preference[PositionPreferenceIds.LAST_POSITION_LNG] = latLng.longitude
            }
        }
    }
}