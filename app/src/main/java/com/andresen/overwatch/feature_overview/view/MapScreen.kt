package com.andresen.overwatch.feature_overview.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    viewModel: TargetOverviewViewModel,
    storeLatestTargetLocation: (LatLng) -> Unit = { },
) {

    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember {
        MapUiSettings(
            zoomControlsEnabled = false,
            myLocationButtonEnabled = true,
            compassEnabled = true
        )
    }

    val currentLocation by viewModel.currentLatLng.collectAsState()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation, 16f)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(MapEvent.ToggleNightVision)
                }
            ) {
                Icon(
                    imageVector = if (viewModel.state.isNightVision) {
                        Icons.Default.ToggleOff
                    } else Icons.Default.ToggleOn,
                    contentDescription = "Toggle night vision"
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        GoogleMap(
            //cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                storeLatestTargetLocation(it)
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            viewModel.state.targets.forEach { target ->
                Marker(
                    position = LatLng(target.lat, target.lng),
                    title = "Target coordinates: ${target.lat}, ${target.lng}",
                    snippet = "Long click to delete",
                    onInfoWindowLongClick = {
                        viewModel.onEvent(
                            MapEvent.OnInfoBoxLongClick(target)
                        )
                    },
                    onClick = {
                        //fetchLatestTargetLocation(LatLng(target.lat, target.lng))
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED
                    )
                )
            }
        }
    }
}