package com.andresen.featureMap.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.andresen.featureMap.mapper.MapMapper
import com.andresen.libraryStyle.R
import com.andresen.featureMap.model.MapContentUi
import com.andresen.featureMap.model.MapUi
import com.andresen.featureMap.model.MarkerUi
import com.andresen.featureMap.viewmodel.MapViewModel
import com.andresen.libraryStyle.theme.OverwatchTheme
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel,
    onDeleteMarkerOnInfoBoxLongClick: (MarkerUi) -> Unit = { },
    onFriendlyInfoWindowClick: (MarkerUi) -> Unit = { },
    onCreateMarkerLongClick: (LatLng) -> Unit = { },
) {

    val mapUiState by viewModel.state.collectAsState(MapMapper.loading())

    val uiState = when (val contentUi = mapUiState.mapContent) {
        is MapContentUi.MapContent -> contentUi
        else -> null
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = true,
                compassEnabled = true
            )
        )
    }

    val cameraPositionState = if (uiState != null) {
        rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                uiState.zoomLocation, 15f
            )
        }
    } else rememberCameraPositionState()


    Scaffold(
        backgroundColor = OverwatchTheme.colors.medium,
        contentColor = OverwatchTheme.colors.contrastLight,
        scaffoldState = scaffoldState,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                onClick = {
                    scope.launch {
                        if (uiState != null) {
                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(
                                    uiState.zoomLocation,
                                    15f
                                ),
                            )
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.unit),
                        contentDescription = stringResource(id = R.string.map_locate_target),
                        tint = Color.Red
                    )
                },
                text = {
                    Text(
                        text = stringResource(id = R.string.map_locate_target),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = OverwatchTheme.colors.mediumLight10,
                contentColor = OverwatchTheme.colors.contrastLight,
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { padding ->
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            properties = uiState?.properties ?: MapProperties(),
            uiSettings = uiSettings,
            onMapLongClick = {
                onCreateMarkerLongClick(it)
            }
        ) {
            when (val contentUi = mapUiState.mapContent) {
                is MapContentUi.MapContent -> {
                    contentUi.markers.forEach { marker ->
                        CreateMarker(
                            marker,
                            onDeleteMarkerOnInfoBoxLongClick,
                            onFriendlyInfoWindowClick
                        )
                    }
                }

                is MapContentUi.Error -> {}
                is MapContentUi.Loading -> {}
            }
        }
    }
}

@Composable
private fun CreateMarker(
    marker: MarkerUi,
    onDeleteMarkerOnInfoBoxLongClick: (MarkerUi) -> Unit = { },
    onInfoWindowClick: (MarkerUi) -> Unit = { },
) {
    Marker(
        state = MarkerState(position = LatLng(marker.lat, marker.lng)),
        title = if (marker.friendly) {
            stringResource(id = R.string.map_marker_friendly)
        } else stringResource(
            id = R.string.map_marker_target
        ),
        snippet = "${marker.lat}, ${marker.lng}",
        onInfoWindowLongClick = {
            if (!marker.friendly) {
                onDeleteMarkerOnInfoBoxLongClick(marker)
            } else {
                onInfoWindowClick(marker)
            }
        },
        draggable = true,
        onClick = {
            it.showInfoWindow()
            true
        },
        icon = if (marker.friendly) {
            bitmapFromVector(LocalContext.current, R.drawable.unit_marker_38)
                ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        } else {
            bitmapFromVector(LocalContext.current, R.drawable.tarket_marker_38)
                ?: BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }
    )
}

private fun bitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    val vectorDrawable: Drawable = ContextCompat.getDrawable(context, vectorResId)!!
    vectorDrawable.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)

    val bitmap: Bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas: Canvas = Canvas(bitmap)

    vectorDrawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}