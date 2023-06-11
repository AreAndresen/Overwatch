package com.andresen.overwatch.main.koinmodules.units

import android.content.Context
import com.andresen.featureUnits.viewmodel.UnitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object UnitsModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                viewModel {
                    UnitViewModel(get())
                }
            }
        )
    }
}


