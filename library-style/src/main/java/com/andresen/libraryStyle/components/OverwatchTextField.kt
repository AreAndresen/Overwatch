package com.andresen.libraryStyle.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andresen.libraryStyle.theme.OverwatchComposableTheme
import com.andresen.libraryStyle.theme.OverwatchTheme

@Composable
fun OverwatchTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    singleLine: Boolean = false,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {

    Box(
        modifier = modifier
            .background(
                color = OverwatchTheme.colors.mediumLight10,
                shape = OverwatchTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart,
    ) {

        var isTextFieldFocused by remember { mutableStateOf(false) }
        val showLabel = remember(isTextFieldFocused, text) {
            isTextFieldFocused || text.isNotEmpty()
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { isTextFieldFocused = it.hasFocus },
                    value = text,
                    singleLine = singleLine,
                    keyboardOptions = keyboardOptions,
                    textStyle = OverwatchTheme.typography.body.copy(color = OverwatchTheme.colors.contrastLight),
                    cursorBrush = SolidColor(OverwatchTheme.colors.contrastLight),
                    onValueChange = onTextChange,

                )
            }
        }

        AnimatedVisibility(
            visible = !showLabel,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = label,
                maxLines = 1,
                style = OverwatchTheme.typography.body,
                color = OverwatchTheme.colors.contrastLight70
            )
        }
    }
}


@Preview
@Composable
private fun DefaultPreview() {
    OverwatchComposableTheme {
        Surface(
            color = OverwatchTheme.colors.medium,
            contentColor = OverwatchTheme.colors.contrastLight
        ) {
            var text by remember { mutableStateOf("") }

            OverwatchTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = text,
                label = "Unit1",
                onTextChange = { text = it }
            )
        }
    }
}
