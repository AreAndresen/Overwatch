package com.andresen.overwatch.koinmodules.targetoverwatch

import com.andresen.feature_overview.TargetOverviewArguments
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object TargetOverwatchModule {
    fun createModules(): List<Module> {
        return listOf(
            module {
                viewModel {// (args: TargetOverviewArguments) ->
                    TargetOverviewViewModel() // arguments = args
                }
            }
        )
    }
}


