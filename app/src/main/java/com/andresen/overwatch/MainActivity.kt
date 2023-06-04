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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.andresen.featureMap.MapEvent
import com.andresen.featureMap.mapper.MapMapper
import com.andresen.featureMap.viewmodel.MapViewModel
import com.andresen.libraryRepositories.components.TopAppBarComposable
import com.andresen.libraryRepositories.theme.OverwatchComposableTheme
import com.andresen.libraryRepositories.theme.OverwatchTheme
import com.andresen.featureUnits.viewmodel.UnitViewModel
import com.andresen.overwatch.main.navigation.OverwatchNavHost
import com.andresen.overwatch.main.navigation.Screen
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private val mapViewModel: MapViewModel by viewModel()
    private val unitViewModel: UnitViewModel by viewModel()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                mapViewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        ) -> {
            mapViewModel.getDeviceLocation(fusedLocationProviderClient)
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
            val mapUiState by mapViewModel.state.collectAsState(MapMapper.loading())
            val unitsUiState by unitViewModel.state.collectAsState()

            OverwatchComposableTheme {
                val navController = rememberNavController()
                val items = listOf(
                    Screen.Chat,
                    Screen.Map,
                    Screen.Units,
                )
                val scaffoldState = rememberScaffoldState()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier,
                    backgroundColor = OverwatchTheme.colors.medium,
                    contentColor = OverwatchTheme.colors.contrastLight,
                    topBar = {
                        TopAppBarComposable(
                            isNightVision = mapUiState.mapTopAppBar.isNightVision,
                            onToggleNightVision = { mapViewModel.onEvent(MapEvent.ToggleNightVision) },
                        )
                    },
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = OverwatchTheme.colors.medium,
                            contentColor = OverwatchTheme.colors.contrastLight,
                        ) {
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            painter = when (screen.route) {
                                                "chat" -> painterResource(id = R.drawable.chat)
                                                "map" -> painterResource(id = R.drawable.map)
                                                "units" -> painterResource(id = R.drawable.units)
                                                else -> painterResource(id = R.drawable.map)
                                            },
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
                        mapUiState = mapUiState,
                        unitsUiState = unitsUiState,
                        onCreateMarkerLongClick = {
                            mapViewModel.createMarker(it)
                        },
                        onDeleteMarkerOnInfoBoxLongClick = { marker ->
                            mapViewModel.onEvent(
                                MapEvent.DeleteMarker(marker)
                            )
                        }
                    )
                }
            }
        }
    }


}

