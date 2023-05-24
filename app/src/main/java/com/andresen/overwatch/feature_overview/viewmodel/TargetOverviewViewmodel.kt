package com.andresen.overwatch.feature_overview.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_overview.view.MapState
import com.andresen.overview.view.MapStyle
import com.andresen.overwatch.feature_overview.model.TargetUi
import com.andresen.overwatch.feature_overview.repository.TargetRepository
import com.andresen.overwatch.feature_overview.view.MapEvent
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class TargetOverviewViewModel(
    private val repository: TargetRepository
) : ViewModel() {

    var state by mutableStateOf(MapState())


    private val _currentLatLng: MutableStateFlow<LatLng> = MutableStateFlow(
        LatLng(
        0.0, // mock oslo
        0.0
        )
    )

    val currentLatLng: StateFlow<LatLng> = _currentLatLng

    init {
        viewModelScope.launch {
            repository.getTargets().collectLatest { targets ->

                val lat = targets.last().lat
                val lng = targets.last().lng
                _currentLatLng.value = LatLng(lat, lng)


                state = state.copy(
                    targets = targets
                )
            }
        }
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

    fun setCurrentLatLng(latLng: LatLng) {
        _currentLatLng.value = latLng
    }
}