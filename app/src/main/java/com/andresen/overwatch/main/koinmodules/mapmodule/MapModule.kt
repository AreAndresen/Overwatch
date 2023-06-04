package com.andresen.overwatch.main.koinmodules.mapmodule

import android.content.Context
import com.andresen.featureMap.viewmodel.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object MapModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                viewModel {
                    MapViewModel(get(), get(), get(), get())
                }
            }
        )
    }
}


