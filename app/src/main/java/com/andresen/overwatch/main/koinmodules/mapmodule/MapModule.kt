package com.andresen.overwatch.main.koinmodules.mapmodule

import android.content.Context
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepository
import com.andresen.overwatch.feature_map.repository.data.local.datastore.PositionPreferenceRepositoryImpl
import com.andresen.overwatch.feature_map.repository.data.local.db.MarkerDatabase
import com.andresen.overwatch.feature_map.repository.data.local.db.MapLocalRepository
import com.andresen.overwatch.feature_map.repository.data.local.db.MapLocalRepositoryImpl
import com.andresen.overwatch.feature_map.repository.data.remote.db.MapRepository
import com.andresen.overwatch.feature_map.repository.data.remote.db.MapRepositoryImpl
import com.andresen.overwatch.feature_map.viewmodel.MapViewModel
import com.andresen.overwatch.feature_units.repository.remote.db.UnitRepository
import com.andresen.overwatch.feature_units.viewmodel.UnitViewModel
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
                single { MarkerDatabase.createDao(get()) }
                single<MapLocalRepository> {
                    MapLocalRepositoryImpl(get())
                }
                single<PositionPreferenceRepository> {
                    PositionPreferenceRepositoryImpl(
                        androidContext(),
                        get()
                    )
                }
                factory { RequestHelper(get()) }
                single { ApiServiceFactoryImpl(get()) }
                single<MapRepository> {
                    MapRepositoryImpl(
                        (get() as ApiServiceFactoryImpl).createService(),
                        get(),
                        get())
                }
                factory {
                    UnitRepository(
                        (get() as ApiServiceFactoryImpl).createService(),
                        get(),
                        get()
                    )
                }
                factory<ConnectionService> { ConnectionServiceImpl(get()) }
                viewModel {
                    MapViewModel(get(), get(), get())
                }
                viewModel {
                    UnitViewModel(get())
                }
                single<OverwatchDispatchers> { OverwatchDispatchersRegular }

            }
        )
    }
}


