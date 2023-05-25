package com.andresen.overwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.andresen.overwatch.feature_overview.theme.OverwatchTheme
import com.andresen.overwatch.feature_overview.view.MapScreen
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : ComponentActivity() {
    //private val mainViewModel: MainViewModel by viewModel()
    private val targetOverviewViewModel: TargetOverviewViewModel by viewModel()

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OverwatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val currentLocation by targetOverviewViewModel.currentLatLng.collectAsState()

                    MapScreen(
                        targetOverviewViewModel,
                        storeLatestTargetLocation = {
                            targetOverviewViewModel.setLastPosition(it)
                            // fetchLocationUpdates()
                        },
                        currentLocation
                    )
                }
            }
        }
    }

    private fun fetchLocationUpdates() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                //fusedLocationClient.locationFlow().collect {
                //it?.let { location ->
                targetOverviewViewModel.setLastPosition(
                    LatLng(
                        59.906494079171864,
                        10.764080956578255
                    )
                ) // location.latitude, location.longitude
            }
            //}
        }
    }
}

