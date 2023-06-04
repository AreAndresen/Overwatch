package com.andresen.overwatch.main.navigation

import androidx.annotation.StringRes
import com.andresen.libraryStyle.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {

    object Map : Screen("map", R.string.map)
    object Units : Screen("units", R.string.units)

    object Chat : Screen("chat", R.string.chat)
}