package com.andresen.overwatch

import android.app.Application
import com.andresen.overwatch.koinmodules.KoinModules
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

class OverwatchMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@OverwatchMapApplication)
            fragmentFactory()
            modules(KoinModules.module(this@OverwatchMapApplication))
        }


    }
}
