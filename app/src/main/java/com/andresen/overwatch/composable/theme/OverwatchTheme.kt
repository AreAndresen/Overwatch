package com.andresen.overwatch.composable.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

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