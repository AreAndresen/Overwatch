package com.andresen.overwatch.feature_units.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.andresen.overwatch.R
import com.andresen.overwatch.feature_map.model.MapUi
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
                is UnitsContentUi.Loading -> {} //LoadingScreen(modifier)
                is UnitsContentUi.Success -> {
                    PhotosGridScreen(state.units, modifier)
                }
                is UnitsContentUi.Error -> {} //ErrorScreen(retryAction, modifier)
            }
        }
    }
}

@Composable
fun PhotosGridScreen(
    units: List<UnitUiModel>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp),
    ) {
        items(units.size) { index ->
            UnitPhotoCard(units[index])

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
            error = painterResource(R.drawable.units),
            placeholder = painterResource(R.drawable.units),
            contentDescription = stringResource(R.string.units),
            contentScale = ContentScale.FillBounds,
        )
    }
}


/*
@Composable
fun UnitsScreen(
    unitsUiState: UnitsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (unitsUiState) {
        is UnitsUiState.Loading -> LoadingScreen(modifier)
        is UnitsUiState.Success -> PhotosGridScreen(unitsUiState.units, modifier)
        is UnitsUiState.Error -> ErrorScreen(retryAction, modifier)
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.units),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun PhotosGridScreen(units: List<UnitUiModel>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = units, key = { unit -> unit.id }) { unit ->
            UnitPhotoCard(unit)
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
            error = painterResource(R.drawable.units),
            placeholder = painterResource(R.drawable.units),
            contentDescription = stringResource(R.string.units),
            contentScale = ContentScale.FillBounds,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    OverwatchComposableTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    OverwatchComposableTheme {
        ErrorScreen({})
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    OverwatchComposableTheme {
        val mockData = List(10) { UnitUiModel("$it", "") }
        PhotosGridScreen(mockData)
    }
}*/

