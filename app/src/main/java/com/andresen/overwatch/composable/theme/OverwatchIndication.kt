package com.andresen.overwatch.composable.theme

import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun createRippleTheme(
    color: Color,
    alphaMultiplier: Float = 1f,
) = object : RippleTheme {
    @Composable
    override fun defaultColor(): Color = color

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleAlpha(
        pressedAlpha = 0.10f * alphaMultiplier,
        focusedAlpha = 0.12f * alphaMultiplier,
        draggedAlpha = 0.08f * alphaMultiplier,
        hoveredAlpha = 0.04f * alphaMultiplier,
    )
}