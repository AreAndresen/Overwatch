package com.andresen.overwatch

import android.app.Application
import com.andresen.overwatch.main.koinmodules.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class OverwatchApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@OverwatchApplication)
            fragmentFactory()
            modules(KoinModules.module(this@OverwatchApplication))
        }


    }
}
