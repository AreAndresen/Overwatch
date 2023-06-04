package com.andresen.overwatch.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.featureMap.model.MapUi
import com.andresen.featureMap.model.MarkerUi
import com.andresen.featureMap.view.MapScreen
import com.andresen.overwatch.feature_chat.view.ChatScreen
import com.andresen.featureUnits.model.UnitsUi
import com.andresen.featureUnits.view.UnitsScreen
import com.google.android.gms.maps.model.LatLng

@Composable
fun OverwatchNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "map",
    mapUiState: MapUi,
    unitsUiState: UnitsUi,
    onCreateMarkerLongClick: (LatLng) -> Unit = { },
    onDeleteMarkerOnInfoBoxLongClick: (MarkerUi) -> Unit = { },
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
                mapUiState = mapUiState,
                onCreateMarkerLongClick = {
                    onCreateMarkerLongClick(it)
                },
                onDeleteMarkerOnInfoBoxLongClick = {
                    onDeleteMarkerOnInfoBoxLongClick(it)
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
                unitsUiState = unitsUiState
            )
        }
    }
}