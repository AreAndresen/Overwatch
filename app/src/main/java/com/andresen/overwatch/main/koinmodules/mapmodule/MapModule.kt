package com.andresen.overwatch.main.koinmodules.mapmodule

import android.content.Context
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepositoryImpl
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetDatabase
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetRepositoryImpl
import com.andresen.overwatch.feature_map.repository.data.remote.db.MapRepository
import com.andresen.overwatch.feature_map.viewmodel.MapViewModel
import com.andresen.overwatch.main.helper.OverwatchDispatchers
import com.andresen.overwatch.main.helper.OverwatchDispatchersRegular
import com.andresen.overwatch.main.helper.network.ApiServiceFactoryImpl
import com.andresen.overwatch.main.helper.network.ConnectionService
import com.andresen.overwatch.main.helper.network.ConnectionServiceImpl
import com.andresen.overwatch.main.helper.network.RequestHelper
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object MapModule {
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
                factory { RequestHelper(get()) }
                single { ApiServiceFactoryImpl(get()) }
                factory { MapRepository((get() as ApiServiceFactoryImpl).createService(), get(), get()) }
                factory<ConnectionService> { ConnectionServiceImpl(get()) }
                viewModel {
                    MapViewModel(get(), get(), get())
                }
                single<OverwatchDispatchers> { OverwatchDispatchersRegular }

            }
        )
    }
}


