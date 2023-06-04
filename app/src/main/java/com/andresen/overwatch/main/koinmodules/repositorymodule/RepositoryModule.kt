package com.andresen.overwatch.main.koinmodules.repositorymodule

import android.content.Context
import com.andresen.libraryRepositories.units.remote.UnitRepository
import com.andresen.libraryRepositories.helper.OverwatchDispatchers
import com.andresen.libraryRepositories.helper.OverwatchDispatchersRegular
import com.andresen.libraryRepositories.helper.network.ApiServiceFactoryImpl
import com.andresen.libraryRepositories.helper.network.ConnectionService
import com.andresen.libraryRepositories.helper.network.ConnectionServiceImpl
import com.andresen.libraryRepositories.helper.network.RequestHelper
import com.andresen.libraryRepositories.map.local.datastore.PositionPreferenceRepository
import com.andresen.libraryRepositories.map.local.datastore.PositionPreferenceRepositoryImpl
import com.andresen.libraryRepositories.map.local.db.MapLocalRepository
import com.andresen.libraryRepositories.map.local.db.MapLocalRepositoryImpl
import com.andresen.libraryRepositories.map.local.db.MarkerDatabase
import com.andresen.libraryRepositories.map.remote.MapGlobalEvent
import com.andresen.libraryRepositories.map.remote.MapRepository
import com.andresen.libraryRepositories.map.remote.MapRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


object RepositoryModule {
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
                        get(),
                        get()
                    )
                }
                factory {
                    UnitRepository(
                        (get() as ApiServiceFactoryImpl).createService(),
                        get(),
                        get()
                    )
                }
                single { MapGlobalEvent() }
                factory<ConnectionService> { ConnectionServiceImpl(get()) }
                single<OverwatchDispatchers> { OverwatchDispatchersRegular }

            }
        )
    }
}


