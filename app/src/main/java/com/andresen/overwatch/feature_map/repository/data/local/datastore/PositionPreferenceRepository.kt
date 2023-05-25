package com.andresen.overwatch.feature_map.repository.data.local.datastore

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface PositionPreferenceRepository {

    val lastPositionLatFlow: Flow<Double>
    val lastPositionLngFlow: Flow<Double>

    suspend fun setLastPositionLatLng(latLng: LatLng)
}