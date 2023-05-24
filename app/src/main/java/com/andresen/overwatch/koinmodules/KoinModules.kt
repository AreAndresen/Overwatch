package com.andresen.overwatch.koinmodules

import android.content.Context
import com.andresen.overwatch.MainViewModel
import com.andresen.overwatch.koinmodules.targetoverwatch.TargetOverwatchModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {

    fun module(context: Context): List<Module> = listOf(
        createCommonModules(),
    )
        .union(TargetOverwatchModule.createModules())
        .toList()

    private fun createCommonModules(): Module {
        return module {

            viewModel { MainViewModel() } // get()
        }
    }


}
