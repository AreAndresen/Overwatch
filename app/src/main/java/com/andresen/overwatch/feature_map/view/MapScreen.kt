package com.andresen.overwatch.feature_map.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.andresen.overwatch.R
import com.andresen.overwatch.feature_map.viewmodel.TargetOverviewViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker


@Composable
fun MapScreen(
    viewModel: TargetOverviewViewModel,
    storeLatestTargetLocation: (LatLng) -> Unit = { },
    //currentLocation: LatLng
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

    /* todo doesnt work - too fast
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation, 16f
        )
    }*/


    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            Box(modifier = Modifier.fillMaxSize()) {
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.BottomStart),
                    onClick = {
                        viewModel.onEvent(MapEvent.LocateLastTarget)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_target),
                            contentDescription = "Locate last target"
                        )
                    },
                    text = {
                        Text(
                            text = "Locate target",
                            fontWeight = FontWeight.Bold
                        )
                    },
                )

                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.BottomEnd),
                    onClick = {
                        viewModel.onEvent(MapEvent.ToggleNightVision)
                    },
                    text = {
                        if (viewModel.state.isNightVision) {
                            Text(text = "Nightvision off", fontWeight = FontWeight.Bold)
                        } else Text(text = "Nightvision on", fontWeight = FontWeight.Bold)
                    },
                    icon = {
                        Icon(
                            imageVector = if (viewModel.state.isNightVision) {
                                Icons.Default.ToggleOff
                            } else Icons.Default.ToggleOn,
                            contentDescription = "Toggle night vision"
                        )
                    }
                )
            }

        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        GoogleMap(
            cameraPositionState = viewModel.state.cameraPositionState, // todo cameraPositionStatetodo
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                storeLatestTargetLocation(it) // use if wanting to store target instead of position
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            val scope = rememberCoroutineScope()
            /*MapEffect(viewModel.state.targets) { map ->
                if (viewModel.state.targets.isNotEmpty()) {
                    map.setOnMapLoadedCallback {
                            scope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLngBounds(
                                        getCenterLocation(),
                                        0
                                    ),
                                )
                            }
                        }
                    }
                }*/


            viewModel.state.targets.forEach { target ->
                Marker(
                    position = LatLng(target.lat, target.lng),
                    title = "TARGET (long click to delete)",
                    snippet = "${target.lat}, ${target.lng}",
                    onInfoWindowLongClick = {
                        viewModel.onEvent(
                            MapEvent.OnInfoBoxLongClick(target)
                        )
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_RED
                    )
                )
            }

            viewModel.state.friendlies.forEach { target ->
                Marker(
                    position = LatLng(target.lat, target.lng),
                    title = "FRIENDLY (long click to delete)",
                    snippet = "${target.lat}, ${target.lng}",
                    onInfoWindowLongClick = {
                        viewModel.onEvent(
                            MapEvent.OnInfoBoxLongClick(target)
                        )
                    },
                    onClick = {
                        it.showInfoWindow()
                        true
                    },
                    icon = BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN
                    )
                )
            }
        }
    }
}