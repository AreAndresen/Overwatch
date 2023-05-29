package com.andresen.overwatch.main.components.composable.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.andresen.overwatch.R


val LocalOverwatchTypography = staticCompositionLocalOf<OverwatchTypography> {
    error("Overwatch Typography not initialized!")
}

internal fun createOverwatchSansTypography() = createTypography(overwatchSansFamily)

internal fun createTypography(fontFamily: FontFamily) = OverwatchTypography(
    extraLargeTitle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 48.sp,
        lineHeight = 48.sp,
        fontWeight = FontWeight.Bold,
    ),
    largeTitle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 38.sp,
        lineHeight = 44.sp,
        fontWeight = FontWeight.Bold,
    ),
    mediumTitle = TextStyle(
        fontFamily = fontFamily,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        fontWeight = FontWeight.ExtraBold,
    ),
    title1 = TextStyle(
        fontFamily = fontFamily,
        fontSize = 27.sp,
        lineHeight = 34.sp,
        letterSpacing = 1.2.sp,
        fontWeight = FontWeight.ExtraBold,
    ),
    title2 = TextStyle(
        fontFamily = fontFamily,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.ExtraBold,
    ),
    title3 = TextStyle(
        fontFamily = fontFamily,
        fontSize = 19.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.ExtraBold,
    ),
    preamble = TextStyle(
        fontFamily = fontFamily,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Normal,
    ),
    headline = TextStyle(
        fontFamily = fontFamily,
        fontSize = 17.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.Bold,
    ),
    body = TextStyle(
        fontFamily = fontFamily,
        fontSize = 17.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight.Normal,
    ),
    callout = TextStyle(
        fontFamily = fontFamily,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Normal,
    ),
    subhead = TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Bold,
    ),
    subheadRegular = TextStyle(
        fontFamily = fontFamily,
        fontSize = 15.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal,
    ),
    footnote = TextStyle(
        fontFamily = fontFamily,
        fontSize = 13.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Normal,
    ),
    caption1 = TextStyle(
        fontFamily = fontFamily,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    caption2 = TextStyle(
        fontFamily = fontFamily,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Normal,
    ),
    label = TextStyle(
        fontFamily = fontFamily,
        fontSize = 11.sp,
        lineHeight = 14.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    ),
    fontFamily = fontFamily
)

private val overwatchSansFamily: FontFamily
    get() = FontFamily(
        Font(R.font.sans),
        Font(R.font.sans_bold, weight = FontWeight.Bold),
    )

@Immutable
data class OverwatchTypography(
    val extraLargeTitle: TextStyle,
    val largeTitle: TextStyle,
    val mediumTitle: TextStyle,
    val title1: TextStyle,
    val title2: TextStyle,
    val title3: TextStyle,
    val preamble: TextStyle,
    val headline: TextStyle,
    val body: TextStyle,
    val callout: TextStyle,
    val subhead: TextStyle,
    val subheadRegular: TextStyle,
    val footnote: TextStyle,
    val caption1: TextStyle,
    val caption2: TextStyle,
    val label: TextStyle,
    val fontFamily: FontFamily,
)