package com.andresen.overwatch.koinmodules.targetoverwatch

import android.content.Context
import com.andresen.overwatch.feature_overview.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_overview.repository.data.local.datastore.PositionPreferenceRepositoryImpl
import com.andresen.overwatch.feature_overview.repository.data.local.db.TargetDatabase
import com.andresen.overwatch.feature_overview.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_overview.repository.data.local.db.TargetRepositoryImpl
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import com.andresen.overwatch.helper.OverwatchDispatchers
import com.andresen.overwatch.helper.OverwatchDispatchersRegular
import org.koin.android.ext.koin.androidContext
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
                single<PositionPreferenceRepository> {
                    PositionPreferenceRepositoryImpl(
                        androidContext(),
                        get()
                    )
                }
                viewModel {
                    TargetOverviewViewModel(get(), get())
                }
                single<OverwatchDispatchers> { OverwatchDispatchersRegular }

            }
        )
    }
}


