package com.andresen.overwatch.feature_overview.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_overview.view.MapState
import com.andresen.overwatch.feature_overview.view.MapStyle
import com.andresen.overwatch.feature_overview.model.TargetUi
import com.andresen.overwatch.feature_overview.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_overview.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_overview.view.MapEvent
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TargetOverviewViewModel(
    private val repository: TargetRepository,
    private val positionPreferenceRepository: PositionPreferenceRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())



    private val _latLocation = MutableStateFlow<Double>(0.0)
    private val _lngLocation = MutableStateFlow<Double>(0.0)
    private val _currentLatLng: MutableStateFlow<LatLng> = MutableStateFlow(
        LatLng(
            0.0, // mock oslo
            0.0
        )
    )

    val currentLatLng: StateFlow<LatLng> = _currentLatLng

    /*val currentLatLng: StateFlow<LatLng> = positionPreferenceRepository.lastPositionLatFlow
        .mapLatest { lat ->
            lat
        }.combine(positionPreferenceRepository.lastPositionLngFlow) { lat, lng ->
            LatLng(lat, lng)
        }.stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = LatLng(0.0, 0.0)
        )*/

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

      /*viewModelScope.launch {
            positionPreferenceRepository.lastPositionLatFlow
                .combine(positionPreferenceRepository.lastPositionLngFlow) { lat, lng ->
                    _currentLatLng.value = LatLng(lat, lng)
                }.collect()
        }*/
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.ToggleNightVision -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if (state.isNightVision) {
                            null
                        } else MapStyleOptions(MapStyle.json)
                    ),
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
            /*positionPreferenceRepository.setLastPositionLatLong(latLng.latitude)
            positionPreferenceRepository.setLastPositionLng(latLng.longitude)*/

            positionPreferenceRepository.setLastPositionLatLng(latLng)
        }
    }
}