package com.andresen.overwatch.feature_map.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_map.view.MapEvent
import com.andresen.overwatch.feature_map.view.MapState
import com.andresen.overwatch.feature_map.view.MapStyle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


class TargetOverviewViewModel(
    private val repository: TargetRepository,
    private val positionPreferenceRepository: PositionPreferenceRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())


    private val _currentLatLng: MutableStateFlow<LatLng> = MutableStateFlow(
        LatLng( // init to fast
            0.0, // mock oslo
            0.0
        )
    )

    val currentLatLng: StateFlow<LatLng> = _currentLatLng


    init {
        viewModelScope.launch {
            repository.getTargets().collectLatest { targets ->
                state = state.copy(
                    targets = targets
                )
            }
        }

        positionPreferenceRepository.lastPositionLatFlow
            .combine(positionPreferenceRepository.lastPositionLngFlow) { lat, lng ->
                _currentLatLng.value = LatLng(lat, lng)
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.ToggleNightVision -> {
                viewModelScope.launch {
                    state = state.copy(
                        properties = state.properties.copy(
                            isMyLocationEnabled = state.lastKnownLocation != null,
                            mapStyleOptions = if (state.isNightVision) {
                                null
                            } else MapStyleOptions(MapStyle.json),
                            mapType = if (state.isNightVision) {
                                MapType.TERRAIN
                            } else MapType.NORMAL
                        ),
                        // goes back to you last position
                        cameraPositionState = CameraPositionState(
                            position = CameraPosition.fromLatLngZoom(
                                LatLng(
                                    state.lastKnownLocation?.latitude ?: 0.0,
                                    state.lastKnownLocation?.longitude ?: 0.0
                                ),
                                16f
                            )
                        )/*.animate( todo fix animation
                            update = CameraUpdateFactory.newLatLngBounds(
                                getCenterLocation(),
                                0
                            )
                        )*/,
                        isNightVision = !state.isNightVision
                    )
                }
            }

            is MapEvent.OnMapLongClick -> {
                viewModelScope.launch {
                    repository.insertTarget(
                        TargetUi(
                            lat = event.latLng.latitude,
                            lng = event.latLng.longitude
                        )
                    )
                }
            }

            is MapEvent.OnInfoBoxLongClick -> {
                viewModelScope.launch {
                    repository.deleteTarget(
                        target = event.target
                    )
                }
            }
        }
    }

    fun getCenterLocation(): LatLngBounds {
        val centerBuilder: LatLngBounds.Builder = LatLngBounds.builder()
        centerBuilder.include(LatLng(_currentLatLng.value.latitude, _currentLatLng.value.longitude))
        return centerBuilder.build()
    }


    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    storeLastKnownLocation(LatLng(task.result.latitude, task.result.longitude))

                    state = state.copy(
                        lastKnownLocation = task.result
                    )
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }


    fun storeLastKnownLocation(latLng: LatLng) {
        viewModelScope.launch {
            positionPreferenceRepository.setLastPositionLatLng(latLng)
        }
    }
}