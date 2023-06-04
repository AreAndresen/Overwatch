package com.andresen.libraryRepositories.map.local.datastore

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface PositionPreferenceRepository {

    val lastPositionLatFlow: Flow<Double?>
    val lastPositionLngFlow: Flow<Double?>

    suspend fun storeLastMarkerPosition(latLng: LatLng)
}