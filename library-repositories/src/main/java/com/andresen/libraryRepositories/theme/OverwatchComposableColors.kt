package com.andresen.libraryRepositories.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.andresen.libraryRepositories.R

val LocalColors = staticCompositionLocalOf<OverwatchColors> {
    error("LocalColours is not initialized!")
}

@Composable
fun createBaseBlueColors() = OverwatchColors(
    dark = colorResource(id = R.color.theme_base_blue_dark),
    dark90 = colorResource(id = R.color.theme_base_blue_dark90),
    medium = colorResource(id = R.color.theme_base_blue_medium),
    mediumLight05 = colorResource(id = R.color.theme_base_blue_medium_light5),
    mediumLight10 = colorResource(id = R.color.theme_base_blue_medium_light10),
    mediumLight20 = colorResource(id = R.color.theme_base_blue_medium_light20),
    mediumLight40 = colorResource(id = R.color.theme_base_blue_medium_light40),
    light = colorResource(id = R.color.theme_base_blue_light),
    contrastLight = colorResource(id = R.color.theme_base_blue_contrast_light),
    contrastDark = colorResource(id = R.color.theme_base_blue_contrast_dark)
)

data class OverwatchColors(
    val dark: Color,
    val dark90: Color,
    val medium: Color,
    val mediumLight05: Color,
    val mediumLight10: Color,
    val mediumLight20: Color,
    val mediumLight40: Color,
    val light: Color,
    val contrastLight: Color,
    val contrastDark: Color,
) {
    val contrastLight70 get() = contrastLight.copy(alpha = 0.7f)
    val contrastLight10 get() = contrastLight.copy(alpha = 0.1f)
}

