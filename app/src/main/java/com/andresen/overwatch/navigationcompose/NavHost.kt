package com.andresen.overwatch.navigationcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_map.view.screens.ChatScreen
import com.andresen.overwatch.feature_map.view.screens.InfoScreen
import com.andresen.overwatch.feature_map.view.screens.MapScreen
import com.andresen.overwatch.feature_map.viewmodel.MapViewModel

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
        composable("info") {
            InfoScreen(modifier = modifier)
        }
    }
}