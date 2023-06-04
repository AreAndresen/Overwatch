package com.andresen.overwatch.feature_units.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andresen.libraryRepositories.theme.OverwatchTheme
import com.andresen.overwatch.R
import com.andresen.overwatch.feature_units.model.UnitUiModel
import com.andresen.overwatch.feature_units.model.UnitsContentUi
import com.andresen.overwatch.feature_units.model.UnitsUi


@Composable
fun UnitsScreen(
    modifier: Modifier = Modifier,
    unitsUiState: UnitsUi,
) {
    val scaffoldState = rememberScaffoldState()
    val state = unitsUiState.unitsContent

    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is UnitsContentUi.Loading -> {} // todo LoadingScreen(modifier)
                is UnitsContentUi.Success -> {
                    UnitsGridScreen(state.units)
                }

                is UnitsContentUi.Error -> {} //todo ErrorScreen(retryAction, modifier)
            }
        }
    }
}

@Composable
fun UnitsGridScreen(
    units: List<UnitUiModel>,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(units.size) { index ->
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomCenter) {
                    UnitPhotoCard(units[index])
                    Icon(
                        modifier = Modifier.size(150.dp),
                        painter = painterResource(R.drawable.unit),
                        contentDescription = null,
                        tint = OverwatchTheme.colors.mediumLight10
                    )
                }
                Text(
                    text = stringResource(id = R.string.unit_id, index.toString()),
                    textAlign = TextAlign.Center,
                    color = OverwatchTheme.colors.mediumLight10,
                    style = OverwatchTheme.typography.title2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun UnitPhotoCard(photo: UnitUiModel, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgSrc)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.error_img),
            placeholder = painterResource(R.drawable.downloading),
            contentDescription = stringResource(R.string.units),
            contentScale = ContentScale.FillBounds,
        )
    }
}


