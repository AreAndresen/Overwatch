package com.andresen.overwatch.koinmodules.targetoverwatch

import android.content.Context
import com.andresen.overwatch.feature_overview.data.local.TargetDatabase
import com.andresen.overwatch.feature_overview.repository.TargetRepository
import com.andresen.overwatch.feature_overview.repository.TargetRepositoryImpl
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object TargetOverwatchModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                //single { TargetRepositoryImpl(get()) }
                single<TargetRepository> { TargetRepositoryImpl(get()) }
                single { TargetDatabase.createDao(get()) }
                // factory { TargetDatabase.createDao(get()) }

                /*viewModel {// (args: TargetOverviewArguments) ->
                    TargetOverviewViewModel(get()) // arguments = args
                }*/
            }
        )
    }
}


