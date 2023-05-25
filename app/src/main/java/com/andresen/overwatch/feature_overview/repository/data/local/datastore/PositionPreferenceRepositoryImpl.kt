package com.andresen.overwatch.feature_overview.repository.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.andresen.overwatch.helper.OverwatchDispatchers
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private val Context.positionDataStore by preferencesDataStore(name = "position")

class PositionPreferenceRepositoryImpl(
    context: Context,
    private val dispatchers: OverwatchDispatchers,
) : PositionPreferenceRepository {

    private val dataStore = context.positionDataStore


    /*override val lastPositionLatLngFlow = dataStore.data.map { preference ->
        val favouriteLevel = preference[PositionPreferenceIds.LAST_POSITION_LAT_LNG] ?: 0
        NewEpisodesPreferenceFavouriteLevel.values()[favouriteLevel]
    } */

    override val lastPositionLatFlow = dataStore.data.map { preference ->
        preference[PositionPreferenceIds.LAST_POSITION_LAT] ?: 0.0
    }

    override val lastPositionLngFlow = dataStore.data.map { preference ->
        preference[PositionPreferenceIds.LAST_POSITION_LNG] ?: 0.0
    }

    override suspend fun getLastPositionLat(): Double {
        return dataStore.data.firstOrNull()?.get(PositionPreferenceIds.LAST_POSITION_LAT) ?: 0.0
    }

    override suspend fun getLastPositionLng(): Double {
        return dataStore.data.firstOrNull()?.get(PositionPreferenceIds.LAST_POSITION_LNG) ?: 0.0
    }


    /*override suspend fun setLastPositionLatLng(latLng: LatLng?) {
        TODO("Not yet implemented")
    }*/

    override suspend fun setLastPositionLat(lat: Double?) {
        withContext(dispatchers.io) {
            dataStore.edit { preference ->
                preference[PositionPreferenceIds.LAST_POSITION_LAT] = lat ?: 0.0
            }
        }
    }

    override suspend fun setLastPositionLng(lng: Double?) {
        withContext(dispatchers.io) {
            dataStore.edit { preference ->
                preference[PositionPreferenceIds.LAST_POSITION_LNG] = lng ?: 0.0
            }
        }
    }

    override suspend fun setLastPositionLatLng(latLng: LatLng) {
        withContext(dispatchers.io) {
            dataStore.edit { preference ->
                preference[PositionPreferenceIds.LAST_POSITION_LAT] = latLng.latitude ?: 0.0
                preference[PositionPreferenceIds.LAST_POSITION_LNG] = latLng.longitude ?: 0.0
            }
        }
    }
}