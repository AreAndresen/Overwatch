package com.andresen.overwatch.main.koinmodules.chat

import android.content.Context
import com.andresen.featureChat.viewmodel.ChatViewModel
import com.andresen.featureUnits.viewmodel.UnitViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


object ChatModule {
    fun createModules(context: Context): List<Module> {
        return listOf(
            module {
                viewModel {
                    ChatViewModel()
                }
            }
        )
    }
}
