package com.andresen.overwatch.feature_map.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_map.MapEvent
import com.andresen.overwatch.feature_map.mapper.MapMapper
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_map.repository.data.remote.db.MapRepository
import com.andresen.overwatch.main.helper.network.DataResult
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber


class MapViewModel(
    private val repository: TargetRepository,
    private val mapRepository: MapRepository,
    private val positionPreferenceRepository: PositionPreferenceRepository
) : ViewModel() {

    private val mutableDeviceLocation: MutableStateFlow<LatLng?> = MutableStateFlow(null)

    private val mutableLastTargetLocation = positionPreferenceRepository.lastPositionLatFlow
        .combine(positionPreferenceRepository.lastPositionLngFlow) { lat, lng ->
            if (lat != null && lng != null) {
                LatLng(lat, lng)
            } else {
                null
            }
        }

    private val mutableTargetMarkers: MutableStateFlow<List<TargetUi>> =
        MutableStateFlow(emptyList())

    private val mutableMapState = MutableStateFlow(MapMapper.loading())

    val state: Flow<MapUi> = mutableMapState
        .combine(mutableDeviceLocation) { mapUi, userLoc ->
            if (userLoc != null) {
                updateDeviceLocation(mapUi, userLoc)
                MapMapper.updateZoomLocation(mapUi, userLoc)
            } else {
                mapUi
            }
        }
        .combine(mutableLastTargetLocation) { mapUi, lastTargetLocation ->
            if (lastTargetLocation != null) {
                MapMapper.updateZoomLocation(mapUi, lastTargetLocation)
            } else {
                mapUi
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = MapMapper.loading()
        )
        .onEach { Timber.d("Updated frontpage") }

    init {
        createMapContent()
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

                is MapEvent.CreateTargetLongClick -> {
                    mutableMapState.update { mapState ->
                        createTargetLongClick(mapState, event)
                    }
                }

                is MapEvent.OnInfoBoxLongClick -> {
                    mutableMapState.update { mapState ->
                        deleteTargetOnInfoBoxLongClick(mapState, event)
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

    private fun updateDeviceLocation(mapUi: MapUi, latLng: LatLng): MapUi {
        return MapMapper.updateDeviceLocation(mapUi, latLng)
    }

    private suspend fun createTargetLongClick(
        mapUi: MapUi,
        event: MapEvent.CreateTargetLongClick
    ): MapUi {
        repository.insertTarget(
            TargetUi(
                lat = event.latLng.latitude,
                lng = event.latLng.longitude
            )
        )

        return MapMapper.updateTargetMarkers(mapUi, mutableTargetMarkers.value)
    }

    private suspend fun deleteTargetOnInfoBoxLongClick(
        mapUi: MapUi,
        event: MapEvent.OnInfoBoxLongClick
    ): MapUi {
        repository.deleteTarget(
            target = event.target
        )

        return MapMapper.updateTargetMarkers(mapUi, mutableTargetMarkers.value)
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
                            zoomLocation = LatLng(0.0, 0.0),
                            userLocation = mutableDeviceLocation.value
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
                    val location = LatLng(task.result.latitude, task.result.longitude)

                    mutableMapState.update { mapState ->
                        updateDeviceLocation(
                            mapState,
                            LatLng(location.latitude, location.longitude)
                        )
                    }
                    mutableDeviceLocation.value = location
                }
            }
        } catch (e: SecurityException) {
            // Show error or something
        }
    }


    fun createTargetMarker(latLng: LatLng) {
        viewModelScope.launch {
            onEvent(MapEvent.CreateTargetLongClick(latLng))
            positionPreferenceRepository.storeLastTargetPosition(latLng)
        }
    }
}