package com.andresen.overwatch.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_chat.view.ChatScreen
import com.andresen.overwatch.feature_map.view.MapScreen
import com.andresen.overwatch.feature_map.viewmodel.MapViewModel
import com.andresen.overwatch.feature_units.view.UnitsScreen

@Composable
fun OverwatchNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "map",
    viewModel: MapViewModel,
    mapUiState: MapUi
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
                viewModel = viewModel,
                mapUiState = mapUiState,
                storeLatestTargetLocation = {
                    viewModel.setLastTargetLocation(it)
                }
            )
        }
        composable("units") {
            UnitsScreen(modifier = modifier)
        }
    }
}