package com.andresen.overwatch.koinmodules.targetoverwatch

import android.content.Context
import com.andresen.overwatch.feature_overview.data.local.TargetDatabase
import com.andresen.overwatch.feature_overview.repository.TargetRepository
import com.andresen.overwatch.feature_overview.repository.TargetRepositoryImpl
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object TargetOverwatchModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                /*single {
                    Retrofit.Builder()
                        .baseUrl("https://google.com")
                        .addConverterFactory(MoshiConverterFactory.create())
                        .build()
                        .create(MyApi::class.java)
                }*/
                single { TargetDatabase.createDao(get()) }
                single<TargetRepository> {
                    TargetRepositoryImpl(get())
                }
                viewModel {
                    TargetOverviewViewModel(get())
                }

            }
        )
    }
}


