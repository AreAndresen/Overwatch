package com.andresen.overwatch.feature_overview.repository.data.local.datastore

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface PositionPreferenceRepository {


    //val lastPositionLatLngFlow: Flow<LatLngData>

    val lastPositionLatFlow: Flow<Double>
    val lastPositionLngFlow: Flow<Double>


    //suspend fun setLastPositionLatLng(latLng: LatLngData?)

    suspend fun setLastPositionLat(lat: Double?)
    suspend fun setLastPositionLng(lng: Double?)

    suspend fun setLastPositionLatLng(latLng: LatLng)

    suspend fun getLastPositionLat(): Double?
    suspend fun getLastPositionLng(): Double?
}