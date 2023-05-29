package com.andresen.overwatch.main.navigation

import androidx.annotation.StringRes
import com.andresen.overwatch.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    object Map : Screen("map", R.string.map)
    object Info : Screen("info", R.string.info)

    object Chat : Screen("chat", R.string.chat)
}