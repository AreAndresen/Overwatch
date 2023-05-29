package com.andresen.overwatch.feature_map.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_map.mapper.MapMapper
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_map.repository.data.remote.db.MapRepository
import com.andresen.overwatch.feature_map.view.MapEvent
import com.andresen.overwatch.main.helper.network.DataResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MapViewModel(
    private val repository: TargetRepository,
    private val mapRepository: MapRepository,
    private val positionPreferenceRepository: PositionPreferenceRepository
) : ViewModel() {


    private val mutableUserLocation: MutableStateFlow<Location?> = MutableStateFlow(null)
    private val mutableZoomLocation: MutableStateFlow<LatLng> = MutableStateFlow(LatLng(0.0, 0.0))
    private val mutableTargetMarkers: MutableStateFlow<List<TargetUi>> =
        MutableStateFlow(emptyList())

    private val mutableMapState = MutableStateFlow(MapMapper.loading())
    val state: StateFlow<MapUi> = mutableMapState


    init {
        createMapContent()

        positionPreferenceRepository.lastPositionLatFlow
            .combine(positionPreferenceRepository.lastPositionLngFlow) { lat, lng ->
                mutableZoomLocation.value = LatLng(lat, lng)

                MapMapper.updateZoomLocation(mutableMapState.value, LatLng(lat, lng))
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: MapEvent) {
        viewModelScope.launch {
            when (event) {
                is MapEvent.ToggleNightVision -> {
                    mutableMapState.update { mapState ->
                        updateToggleNightVision(mapState)
                    }
                }

                is MapEvent.UpdateZoomLocation -> {
                    mutableMapState.update { mapState ->
                        updateZoomLocation(mapState, event.latLng)
                    }
                }

                is MapEvent.OnMapLongClick -> {
                    mutableMapState.update { mapState ->
                        updateInsertTargetMarkers(mapState, event)
                    }
                }

                is MapEvent.OnInfoBoxLongClick -> {
                    mutableMapState.update { mapState ->
                        updateDeleteTargetMarkers(mapState, event)
                    }
                }

                is MapEvent.CheckFriendlies -> TODO()
            }
        }
    }

    private fun updateToggleNightVision(mapUi: MapUi): MapUi {
        return MapMapper.updateToggleNightVision(mapUi)
    }


    private fun updateZoomLocation(mapUi: MapUi, latLng: LatLng): MapUi {
        return MapMapper.updateZoomLocation(mapUi, latLng)
    }


    private suspend fun updateInsertTargetMarkers(
        mapUi: MapUi,
        event: MapEvent.OnMapLongClick
    ): MapUi {
        repository.insertTarget(
            TargetUi(
                lat = event.latLng.latitude,
                lng = event.latLng.longitude
            )
        )

        return MapMapper.updateInsertTargetMarkers(mapUi, mutableTargetMarkers.value)
    }

    private suspend fun updateDeleteTargetMarkers(
        mapUi: MapUi,
        event: MapEvent.OnInfoBoxLongClick
    ): MapUi {
        repository.deleteTarget(
            target = event.target
        )

        return MapMapper.updateDeleteTargetMarkers(mapUi, mutableTargetMarkers.value)
    }

    private fun updateDeviceLocation(mapUi: MapUi, latLng: LatLng): MapUi {
        return MapMapper.updateDeviceLocation(mapUi, latLng)
    }

    private fun createMapContent() {
        viewModelScope.launch {
            when (val friendliesResult = mapRepository.getFriendlies()) {
                is DataResult.Success -> {
                    val friendliesDto = friendliesResult.data


                    repository.getTargets().collectLatest { targets ->
                        mutableMapState.value = MapMapper.createMapContent(
                            targets = targets,
                            friendlies = friendliesDto,
                            zoomLocation = mutableZoomLocation.value,
                            userLocation = mutableUserLocation.value
                        )
                    }
                }

                is DataResult.Error.AppError -> mutableMapState.value = MapMapper.error()
                is DataResult.Error.NoNetwork -> mutableMapState.value = MapMapper.error()
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
                    val location = task.result

                    mutableUserLocation.value = location

                    // todo - if i want to use this for something like rendering animation at init
                    mutableMapState.update { mapState ->
                        updateDeviceLocation(mapState,LatLng(location.latitude, location.longitude))
                        //updateZoomLocation(mapState, LatLng(location.latitude, location.longitude))
                    }
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }


    fun setLastTargetLocation(latLng: LatLng) {
        viewModelScope.launch {
            mutableMapState.update { mapState ->
                updateZoomLocation(mapState, latLng)
            }

            // store to data
            positionPreferenceRepository.storeLastTargetPosition(latLng)
        }
    }
}