package com.andresen.overwatch.composable.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresen.overwatch.composable.theme.OverwatchComposableTheme
import com.andresen.overwatch.composable.theme.OverwatchTheme

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    enabled: Boolean = true,
    colors: ButtonColors = with(OverwatchTheme.colors) {
        ButtonDefaults.buttonColors(
            backgroundColor = mediumLight10,
            contentColor = contrastLight,
            disabledBackgroundColor = dark,
            disabledContentColor = contrastLight70
        )
    },
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier,
        contentPadding = contentPadding,
        enabled = enabled,
        colors = colors,
        onClick = onClick,
        shape = RoundedCornerShape(50),
        border = BorderStroke(1.5.dp, colors.contentColor(enabled = true).value),
        content = {
            CompositionLocalProvider(LocalTextStyle provides OverwatchTheme.typography.subhead) {
                content()
            }
        }
    )

}

@Preview
@Composable
private fun RoundButtonPreview() {
    OverwatchComposableTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            RoundButton(onClick = { }) {
                Text(text = "Rundknapp")
            }
        }
    }
}