package com.andresen.overwatch.main.koinmodules

import android.content.Context
import com.andresen.overwatch.MainViewModel
import com.andresen.overwatch.main.koinmodules.chat.ChatModule
import com.andresen.overwatch.main.koinmodules.map.MapModule
import com.andresen.overwatch.main.koinmodules.repository.RepositoryModule
import com.andresen.overwatch.main.koinmodules.units.UnitsModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object KoinModules {

    fun module(context: Context): List<Module> = listOf(
        createCommonModules(),
    )
        .union(MapModule.createModules(context))
        .union(UnitsModule.createModules(context))
        .union(ChatModule.createModules(context))
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