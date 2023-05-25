package com.andresen.overwatch.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresen.overwatch.theme.OverwatchTheme
import com.andresen.overwatch.theme.Purple200

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
    enabled: Boolean = true,
    colors: ButtonColors =
        ButtonDefaults.buttonColors(
            backgroundColor = Purple200,
            contentColor = Color.White,
            disabledBackgroundColor = Color.Black,
            disabledContentColor = Color.White
        )
    ,
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
            CompositionLocalProvider(LocalTextStyle provides TextStyle.Default) {
                content()
            }
        }
    )

}
@Preview
@Composable
private fun RoundButtonPreview() {
    OverwatchTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            RoundButton(onClick = { }) {
                Text(text = "Rundknapp")
            }
        }
    }
}