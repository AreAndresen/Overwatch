package com.andresen.overwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import com.andresen.overwatch.feature_overview.view.MapScreen
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.compose.material.Surface
import com.andresen.overwatch.feature_overview.repository.TargetRepository
import com.andresen.overwatch.feature_overview.theme.OverwatchTheme
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.getViewModel


class MainActivity : ComponentActivity() {
    /*private val mainViewModel: MainViewModel by viewModel()
    private val targetOverviewViewModel: TargetOverviewViewModel by viewModel()*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OverwatchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModel = getViewModel<TargetOverviewViewModel>()
                    MapScreen(viewModel)
                }
            }
        }
    }
}
