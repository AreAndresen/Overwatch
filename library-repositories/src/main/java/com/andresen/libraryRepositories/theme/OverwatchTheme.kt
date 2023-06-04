package com.andresen.libraryRepositories.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.andresen.libraryRepositories.theme.LocalColors
import com.andresen.libraryRepositories.theme.LocalOverwatchShapes
import com.andresen.libraryRepositories.theme.LocalOverwatchTypography
import com.andresen.libraryRepositories.theme.OverwatchColors
import com.andresen.libraryRepositories.theme.OverwatchShapes
import com.andresen.libraryRepositories.theme.OverwatchTypography
import com.andresen.libraryRepositories.theme.createBaseBlueColors
import com.andresen.libraryRepositories.theme.createOverwatchSansTypography
import com.andresen.libraryRepositories.theme.createOverwatchShapes

@Composable
fun OverwatchComposableTheme(
    overwatchColors: OverwatchColors = createBaseBlueColors(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides overwatchColors,
        LocalOverwatchTypography provides createOverwatchSansTypography(),
        LocalOverwatchShapes provides createOverwatchShapes(),

        LocalIndication provides rememberRipple(),

        content = { content() }
    )
}

object OverwatchTheme {
    val colors: OverwatchColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: OverwatchTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalOverwatchTypography.current

    val shapes: OverwatchShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalOverwatchShapes.current
}