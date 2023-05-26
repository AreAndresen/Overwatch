package com.andresen.overwatch.feature_map.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_map.mapper.TargetMapper
import com.andresen.overwatch.feature_map.model.MapUiState
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_map.repository.data.remote.db.MapRepository
import com.andresen.overwatch.feature_map.view.MapEvent
import com.andresen.overwatch.feature_map.view.MapState
import com.andresen.overwatch.feature_map.view.MapStyle
import com.andresen.overwatch.helper.network.DataResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.MapType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


class TargetOverviewViewModel(
    private val repository: TargetRepository,
    private val mapRepository: MapRepository,
    private val positionPreferenceRepository: PositionPreferenceRepository
) : ViewModel() {

    // todo
    var mapUiState: MapUiState by mutableStateOf(MapUiState.Loading)

    var state by mutableStateOf(MapState())

    init {
        viewModelScope.launch {
            repository.getTargets().collectLatest { targets ->
                state = state.copy(
                    targets = targets
                )
            }
        }

        getFriendlies()

        positionPreferenceRepository.lastPositionLatFlow
            .combine(positionPreferenceRepository.lastPositionLngFlow) { lat, lng ->
                state = state.copy(
                    lastKnownLocation = LatLng(lat, lng)
                )
            }
            .launchIn(viewModelScope)
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
                                state.lastKnownLocation ?: LatLng(59.910814436867405, 10.752501860260963), // mock Oslo S
                                16f
                            )
                        ),
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

            /*is MapEvent.CheckFriendlies -> {
                viewModelScope.launch {
                    repository.insertTarget(
                        TargetUi(
                            friendly = false,
                            lat = event.latLng.latitude,
                            lng = event.latLng.longitude
                        )
                    )
                }
            } */

            is MapEvent.OnInfoBoxLongClick -> {
                viewModelScope.launch {
                    repository.deleteTarget(
                        target = event.target
                    )
                }
            }
        }
    }

    private fun getFriendlies() {
        viewModelScope.launch {
            when (val friendliesResult = mapRepository.getFriendlies()) {
                is DataResult.Success -> {
                    val friendliesDto = friendliesResult.data

                    val friendlies = TargetMapper.mapFriendlies(friendliesDto)

                    state = state.copy(
                        friendlies = friendlies
                    )
                }

                is DataResult.Error.AppError -> TODO()
                is DataResult.Error.NoNetwork -> TODO()
            }

        }
    }


    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // todo - if i want to use this for something like rendering animation at init
                    /*state = state.copy(
                       lastKnownLocation = task.result
                   )*/
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }


    fun storeLastKnownLocation(latLng: LatLng) {
        viewModelScope.launch {
            state = state.copy(
                lastKnownLocation = latLng
            )
            // store to data
            positionPreferenceRepository.setLastPositionLatLng(latLng)
        }
    }
}