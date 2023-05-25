package com.andresen.overwatch.feature_map.view

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.andresen.overwatch.feature_map.viewmodel.TargetOverviewViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    viewModel: TargetOverviewViewModel,
    storeLatestTargetLocation: (LatLng) -> Unit = { },
    currentLocation: LatLng
) {

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

    // todo doesnt work - too fast
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation, 16f
        )
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
            cameraPositionState = viewModel.state.cameraPositionState,
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                storeLatestTargetLocation(it)
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            val builder = LatLngBounds.Builder()

            viewModel.state.targets.forEach { target ->

                builder.include(LatLng(target.lat, target.lng))

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
            // todo doesnt work
            cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(builder.build(), 64))
        }
    }
}