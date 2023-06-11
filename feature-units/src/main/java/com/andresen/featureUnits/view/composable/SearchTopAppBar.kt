package com.andresen.featureUnits.view.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andresen.featureUnits.R
import com.andresen.libraryStyle.extensions.rememberStringResource
import com.andresen.libraryStyle.theme.OverwatchTheme

@Composable
fun SearchBarCompose(
    modifier: Modifier = Modifier,
    searchText: String,
    onSearchClick: (String) -> Unit = { },
    onClearSearch: () -> Unit = { },
) {
    Box(
        modifier = modifier
            .background(
                color = OverwatchTheme.colors.mediumLight10,
                shape = OverwatchTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        var value by remember { mutableStateOf(searchText) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = value,
                onValueChange = { value = it },
                placeholder = {
                    Text(
                        text = rememberStringResource(id = R.string.search_label),
                        style = OverwatchTheme.typography.body,
                        color = OverwatchTheme.colors.contrastLight70
                    )
                },
                maxLines = 1,
                textStyle = OverwatchTheme.typography.body.copy(color = OverwatchTheme.colors.contrastLight),
                trailingIcon = {
                    IconButton(onClick = {
                        value = "" // todo check why viewmodel isnt updating this to empty
                        onClearSearch()
                    }) {
                        Icon(
                            painter = painterResource(id = com.andresen.libraryStyle.R.drawable.x_remove),
                            contentDescription = stringResource(id = com.andresen.libraryStyle.R.string.map_edit),
                            tint = OverwatchTheme.colors.contrastLight
                        )
                    }
                }
            )
            IconButton(onClick = { onSearchClick(value) }) {
                Icon(
                    painter = painterResource(id = com.andresen.libraryStyle.R.drawable.search),
                    contentDescription = stringResource(id = com.andresen.libraryStyle.R.string.unit_id),
                    tint = OverwatchTheme.colors.contrastLight
                )
            }
        }
    }

}

