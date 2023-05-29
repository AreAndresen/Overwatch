package com.andresen.overwatch.feature_map.view.screens

import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andresen.overwatch.R
import com.andresen.overwatch.composable.theme.OverwatchTheme
import com.andresen.overwatch.feature_map.model.MapContentUi
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.view.MapEvent
import com.andresen.overwatch.feature_map.viewmodel.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@Composable
fun MapScreen(
    modifier: Modifier,
    viewModel: MapViewModel,
    mapUiState: MapUi,
    updateZoomLocation: (LatLng) -> Unit = { },
    storeLatestTargetLocation: (LatLng) -> Unit = { },
) {


    val uiState =  when (val contentUi = mapUiState.mapContent) {
        is MapContentUi.MapContent -> contentUi
        else -> null
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = true,
                compassEnabled = true
            )
        )
    }



    val mapProperties by remember {
        mutableStateOf(
            uiState?.properties
        )
    }


     // todo doesnt work - too fast
    val cameraPositionState = if(uiState != null) {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                uiState.zoomLocation
                , 15f)
        }
    } else rememberCameraPositionState()



    Scaffold(
        backgroundColor = OverwatchTheme.colors.medium,
        contentColor = OverwatchTheme.colors.contrastLight,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                onClick = {
                    scope.launch {
                        if(uiState != null) {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(
                                    uiState.zoomLocation,
                                    15f
                                ),
                            )
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.map),
                        contentDescription = stringResource(id = R.string.map_locate_target),
                        tint = Color.Red
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.map_locate_target),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = OverwatchTheme.colors.mediumLight10,
                contentColor = OverwatchTheme.colors.contrastLight,
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            properties = mapProperties ?: MapProperties(), //viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                storeLatestTargetLocation(it)
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            when (val contentUi = mapUiState.mapContent) {
                is MapContentUi.MapContent -> {
                    contentUi.targets.forEach { target ->
                        createMarker(target, viewModel)
                    }
                    contentUi.friendlies.forEach { target ->
                        createMarker(target, viewModel)
                    }
                    if(uiState != null) {
                        MapEffect(contentUi.userLocation) { map ->
                            map.setOnMapLoadedCallback {
                                scope.launch {
                                    cameraPositionState.animate(
                                        update = CameraUpdateFactory.newLatLngZoom(
                                            uiState.zoomLocation,
                                            15f
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun CameraPositionState.centerOnLocation(
    location: Location
) = animate(
    update = CameraUpdateFactory.newLatLngZoom(
        LatLng(location.latitude, location.longitude),
        15f
    ),
)

@Composable
private fun createMarker(
    target: TargetUi,
    viewModel: MapViewModel
) {
    Marker(
        state = MarkerState(position = LatLng(target.lat, target.lng)),
        title = if (target.friendly) stringResource(id = R.string.map_marker_friendly) else stringResource(
            id = R.string.map_marker_target
        ),
        snippet = "${target.lat}, ${target.lng}",
        onInfoWindowLongClick = {
            viewModel.onEvent(
                MapEvent.OnInfoBoxLongClick(target)
            )
        },
        draggable = true,
        onClick = {
            it.showInfoWindow()
            true
        },
        icon = BitmapDescriptorFactory.defaultMarker(
            if (target.friendly) BitmapDescriptorFactory.HUE_GREEN else BitmapDescriptorFactory.HUE_RED
        )
    )
}