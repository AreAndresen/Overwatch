package com.andresen.overwatch.main.koinmodules

import android.content.Context
import com.andresen.overwatch.MainViewModel
import com.andresen.overwatch.main.koinmodules.mapmodule.MapModule
import com.andresen.overwatch.main.koinmodules.repositorymodule.RepositoryModule
import com.andresen.overwatch.main.koinmodules.unitsmodule.UnitsModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {

    fun module(context: Context): List<Module> = listOf(
        createCommonModules(),
    )
        .union(MapModule.createModules(context))
        .union(UnitsModule.createModules(context))
        .union(RepositoryModule.createModules(context))
        .toList()

    private fun createCommonModules(): Module {
        return module {

            viewModel {
                MainViewModel()
            }

        }
    }
}