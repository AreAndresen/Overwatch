package com.andresen.overwatch.composable.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.andresen.overwatch.R
import com.andresen.overwatch.composable.modifiers.noIndicationClickable
import com.andresen.overwatch.composable.theme.OverwatchTheme

@Composable
fun MapTopAppBar(
    onMapIconClick: () -> Unit,
    onInfoIconClick: () -> Unit
) {
    OverwatchTopAppBar(
        title = "",
        navigationIcon = {
            IconButton(onClick = { onMapIconClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_place_24),
                    tint = OverwatchTheme.colors.contrastLight,
                    contentDescription = stringResource(id = R.string.accessibility_home_button)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onInfoIconClick()
                },
                enabled = true
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_send_to_mobile_24),
                    tint = OverwatchTheme.colors.contrastLight,
                    contentDescription = stringResource(id = R.string.map_accessibility_top_bar_menu_button)
                )
            }
        }
    )
}

@Composable
private fun OverwatchTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    elevation: Dp = 0.dp,
    backgroundColor: Color = Color.Transparent,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = { },
    onTitleClick: () -> Unit = { }
) {
    TopAppBar(
        modifier = modifier.noIndicationClickable { onTitleClick() },
        title = {
            Text(
                text = title,
                style = OverwatchTheme.typography.headline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = OverwatchTheme.colors.contrastLight
            )
        },
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = backgroundColor,
        elevation = elevation,
    )
}