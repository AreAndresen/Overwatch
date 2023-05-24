package com.andresen.overwatch.koinmodules

import android.content.Context
import com.andresen.overwatch.MainViewModel
import com.andresen.overwatch.feature_overview.data.local.TargetDatabase
import com.andresen.overwatch.feature_overview.repository.TargetRepository
import com.andresen.overwatch.feature_overview.repository.TargetRepositoryImpl
import com.andresen.overwatch.feature_overview.viewmodel.TargetOverviewViewModel
import com.andresen.overwatch.koinmodules.targetoverwatch.TargetOverwatchModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
/*
object KoinModules {

    fun module(context: Context): List<Module> = listOf(
        createCommonModules(),
    )
        .union(TargetOverwatchModule.createModules(context))
        .toList()

    private fun createCommonModules(): Module {
        return module {

            /*single<TargetRepository> { TargetRepositoryImpl(get()) }
            factory { TargetDatabase.createDao(get()) }*/

            viewModel { TargetOverviewViewModel(get()) } // get()
            viewModel { MainViewModel() } // get()


        }
    }


} */

val appModule = module {
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
    viewModel {
        MainViewModel()
    }
}