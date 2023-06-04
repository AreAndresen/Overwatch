package com.andresen.overwatch.main.koinmodules.unitsmodule

import android.content.Context
import com.andresen.libraryRepositories.helper.network.ApiServiceFactoryImpl
import com.andresen.overwatch.feature_units.repository.remote.db.UnitRepository
import com.andresen.overwatch.feature_units.viewmodel.UnitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object UnitsModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                // todo move this to own repo for Units
                factory {
                    UnitRepository(
                        (get() as ApiServiceFactoryImpl).createService(),
                        get(),
                        get()
                    )
                }
                viewModel {
                    UnitViewModel(get())
                }
            }
        )
    }
}


