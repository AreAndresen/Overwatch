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
import com.andresen.overwatch.feature_map.viewmodel.TargetOverviewViewModel

@Composable
fun MapTopAppBar(
    viewModel: TargetOverviewViewModel,
    onToggleNightVision: () -> Unit
) {
    OverwatchTopAppBar(
        title = "",
        navigationIcon = {
            IconButton(onClick = { onToggleNightVision() }) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = stringResource(id = R.string.map_edit)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    onToggleNightVision()
                },
                enabled = true
            ) {
                Icon(
                    painter = if (viewModel.state.isNightVision) {
                        painterResource(id = R.drawable.visibility_on)
                    } else painterResource(id = R.drawable.visibility_off),
                    contentDescription = stringResource(id = R.string.map_nightvision_toggle_desc)
                )
                /*Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = stringResource(id = R.string.map_accessibility_top_bar_menu_button)
                )*/
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