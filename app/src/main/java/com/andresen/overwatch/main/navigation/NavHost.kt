package com.andresen.overwatch.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.featureChat.view.ChatScreen
import com.andresen.featureMap.MapEvent
import com.andresen.featureMap.mapper.MapMapper
import com.andresen.featureMap.model.MapUi
import com.andresen.featureMap.model.MarkerUi
import com.andresen.featureMap.view.MapScreen
import com.andresen.featureMap.viewmodel.MapViewModel
import com.andresen.featureUnits.model.UnitsUi
import com.andresen.featureUnits.view.UnitsScreen
import com.andresen.featureUnits.viewmodel.UnitViewModel
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.viewModel

@Composable
fun OverwatchNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "map",
    mapViewModel: MapViewModel = viewModel(),
    unitViewModel: UnitViewModel = viewModel()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("chat") {
            ChatScreen(modifier = modifier)
        }
        composable("map") {
            MapScreen(
                modifier = modifier,
                viewModel = mapViewModel,
                onCreateMarkerLongClick = {
                    mapViewModel.createMarker(it)
                },
                onDeleteMarkerOnInfoBoxLongClick = { marker ->
                    mapViewModel.onEvent(
                        MapEvent.DeleteMarker(marker)
                    )
                },
                onFriendlyInfoWindowClick = {
                    navController.navigate("units") {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable("units") {
            UnitsScreen(
                modifier = modifier,
                viewModel = unitViewModel
            )
        }
    }
}