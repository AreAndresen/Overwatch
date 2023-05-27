package com.andresen.overwatch

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresen.overwatch.composable.components.MapTopAppBar
import com.andresen.overwatch.composable.theme.OverwatchComposableTheme
import com.andresen.overwatch.composable.theme.OverwatchTheme
import com.andresen.overwatch.feature_map.viewmodel.TargetOverviewViewModel
import com.andresen.overwatch.navigationcompose.OverwatchNavHost
import com.andresen.overwatch.navigationcompose.Screen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val targetOverviewViewModel: TargetOverviewViewModel by viewModel()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                targetOverviewViewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        ) -> {
            targetOverviewViewModel.getDeviceLocation(fusedLocationProviderClient)
        }

        else -> {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()
        setContent {
            OverwatchComposableTheme {
                val navController = rememberNavController()
                val items = listOf(
                    Screen.Map,
                    Screen.Info,
                )
                val scaffoldState = rememberScaffoldState()

                Scaffold(
                    modifier = Modifier,
                    backgroundColor = OverwatchTheme.colors.medium,
                    contentColor = OverwatchTheme.colors.contrastLight,
                    topBar = {
                        MapTopAppBar(
                            onMapIconClick = {},
                            onInfoIconClick = {},
                        )
                    },
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            Icons.Filled.Favorite,
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    OverwatchNavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "map",
                        viewModel = targetOverviewViewModel
                    )
                }
            }
        }
    }


}

