package com.andresen.overwatch.navigationcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andresen.overwatch.feature_map.view.screens.ChatScreen
import com.andresen.overwatch.feature_map.view.screens.InfoScreen
import com.andresen.overwatch.feature_map.view.screens.MapScreen
import com.andresen.overwatch.feature_map.viewmodel.TargetOverviewViewModel

@Composable
fun OverwatchNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "map",
    viewModel: TargetOverviewViewModel,
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
                storeLatestTargetLocation = {
                    viewModel.storeLastKnownLocation(it)
                }
            )
        }
        composable("info") {
            InfoScreen(modifier = modifier,)
        }
    }
}