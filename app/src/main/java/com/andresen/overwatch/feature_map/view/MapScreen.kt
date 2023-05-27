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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andresen.overwatch.R
import com.andresen.overwatch.composable.theme.OverwatchTheme
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.viewmodel.TargetOverviewViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreen(
    modifier: Modifier,
    viewModel: TargetOverviewViewModel,
    storeLatestTargetLocation: (LatLng) -> Unit = { },
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
        viewModel.state.cameraPositionState
    }

    Scaffold(
        backgroundColor = OverwatchTheme.colors.medium,
        contentColor = OverwatchTheme.colors.contrastLight,
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
                ExtendedFloatingActionButton(
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .align(alignment = Alignment.BottomEnd),
                    onClick = {
                        viewModel.onEvent(MapEvent.ToggleNightVision)
                    },
                    text = {
                        if (viewModel.state.isNightVision) {
                            Text(
                                text = stringResource(id = R.string.map_nightvision_off),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold
                            )
                        } else Text(
                            text = stringResource(id = R.string.map_nightvision_on),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    backgroundColor = OverwatchTheme.colors.mediumLight10,
                    contentColor = OverwatchTheme.colors.contrastLight,
                    icon = {
                        Icon(
                            imageVector = if (viewModel.state.isNightVision) {
                                Icons.Default.ToggleOff
                            } else Icons.Default.ToggleOn,
                            contentDescription = stringResource(id = R.string.map_nightvision_toggle_desc)
                        )
                    }
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState, //viewModel.state.cameraPositionState, //cameraPositionState,
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {
                storeLatestTargetLocation(it)
                viewModel.onEvent(MapEvent.OnMapLongClick(it))
            }
        ) {
            viewModel.state.targets.forEach { target ->
                createMarker(target, viewModel)
            }
            viewModel.state.friendlies.forEach { target ->
                createMarker(target, viewModel)
            }

            // todo get camera to Zoom at launch to GPS location
            /*val scope = rememberCoroutineScope()
            MapEffect(viewModel.state.targets) { map ->
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
        }
    }
}

@Composable
private fun createMarker(
    target: TargetUi,
    viewModel: TargetOverviewViewModel,
) {
    Marker(
        position = LatLng(target.lat, target.lng),
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