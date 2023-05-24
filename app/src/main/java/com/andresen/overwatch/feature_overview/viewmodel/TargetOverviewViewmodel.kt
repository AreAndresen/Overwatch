package com.andresen.overwatch.feature_overview.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.andresen.overwatch.feature_overview.view.MapEvent
import com.andresen.feature_overview.view.MapState
import com.andresen.overview.view.MapStyle
import com.google.android.gms.maps.model.MapStyleOptions
import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import com.andresen.feature_overview.TargetOverviewArguments


class TargetOverviewViewModel(
    //arguments: TargetOverviewArguments?,
) : ViewModel() {

    var state by mutableStateOf(MapState())

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.ToggleNightVision -> {
                state = state.copy(
                    properties = state.properties.copy(
                        mapStyleOptions = if(state.isNightVision) {
                           null
                        } else MapStyleOptions(MapStyle.json)
                    ),
                    isNightVision = !state.isNightVision
                )
            }
        }
    }
}