package com.andresen.libraryStyle.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

val LocalOverwatchShapes = staticCompositionLocalOf<OverwatchShapes> {
    error("LocalShapes not initialized!")
}

data class OverwatchShapes(
    val small: Shape,
    val medium: Shape,
    val large: Shape
)

internal fun createOverwatchShapes() = OverwatchShapes(
    small = RoundedCornerShape(2.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(6.dp)
)