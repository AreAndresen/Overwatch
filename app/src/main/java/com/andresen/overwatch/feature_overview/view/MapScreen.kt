package com.andresen.overwatch.feature_overview.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings

@Composable
fun MapScreen(
    viewModel: TargetOverviewViewModel
) {

    val scaffoldState = rememberScaffoldState()
    val uiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(MapEvent.ToggleNightVision)
            }) {
                Icon(
                    imageVector = if (viewModel.state.isNightVision) {
                        Icons.Default.ToggleOff
                    } else Icons.Default.ToggleOn,
                    contentDescription = "Toggle night vision"
                )
            }
        }
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = viewModel.state.properties,
            uiSettings = uiSettings,
            onMapLongClick = {

            }
        )
    }
}