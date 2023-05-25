package com.andresen.overwatch.feature_map.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_map.view.MapState
import com.andresen.overwatch.feature_map.view.MapStyle
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_map.view.MapEvent
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
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
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if (state.isNightVision) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                        mapType = if (state.isNightVision) {
                            MapType.TERRAIN
                        } else MapType.NORMAL
                    ),
                    cameraPositionState = CameraPositionState(
                        position = CameraPosition.fromLatLngZoom(
                            LatLng(_currentLatLng.value.latitude, _currentLatLng.value.longitude), 16f
                        )),
                    isNightVision = !state.isNightVision
                )
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

    /*fun setCurrentLatLng(latLng: LatLng) {
        _currentLatLng.value = latLng
    }*/

    fun setLastPosition(latLng: LatLng) {
        viewModelScope.launch {
            positionPreferenceRepository.setLastPositionLatLng(latLng)
        }
    }
}