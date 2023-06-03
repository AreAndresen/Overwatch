package com.andresen.overwatch.feature_map.mapper

import com.andresen.overwatch.feature_map.model.MapContentUi
import com.andresen.overwatch.feature_map.model.MapTopAppBar
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_map.model.MarkerUi
import com.andresen.overwatch.feature_map.repository.data.local.db.MarkerEntity
import com.andresen.overwatch.feature_map.repository.data.remote.db.MarkerDto
import com.andresen.overwatch.feature_map.repository.data.remote.db.MarkerWrapperDto
import com.andresen.overwatch.main.components.composable.theme.MapStyle
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

object MapMapper {

    fun loading(): MapUi = MapUi(
        mapTopAppBar = MapTopAppBar(
            isNightVision = false
        ),
        mapContent = MapContentUi.Loading
    )

    fun error(): MapUi = MapUi(
        mapTopAppBar = MapTopAppBar(
            isNightVision = false
        ),
        mapContent = MapContentUi.Error
    )


    fun createMapContent(
        localMarkers: List<MarkerUi>,
        markersDto: MarkerWrapperDto,
        zoomLocation: LatLng,
        userLocation: LatLng? = null,
    ): MapUi {
        val markersDtoMapped = mapDtoMarkers(markersDto)

        val allMarkers = mutableListOf<MarkerUi>()
        allMarkers.addAll(localMarkers)
        markersDtoMapped.map { marker ->
            allMarkers.add(marker)
        }

        return MapUi(
            mapTopAppBar = MapTopAppBar(
                isNightVision = false
            ),
            mapContent = MapContentUi.MapContent(
                properties = MapProperties(
                    isMyLocationEnabled = userLocation != null,
                    mapType = MapType.NORMAL
                ),
                userLocation = userLocation,
                zoomLocation = zoomLocation,
                markers = allMarkers,
            )
        )
    }


    fun updateToggleNightVision(
        mapUi: MapUi,
    ): MapUi {
        val mapTopAppBar = mapUi.mapTopAppBar
        val mapContent = mapUi.mapContent
        return mapUi.copy(
            mapTopAppBar = mapTopAppBar.copy(
                isNightVision = !mapTopAppBar.isNightVision
            ),
            mapContent = if (mapContent is MapContentUi.MapContent) {
                mapContent.copy(
                    properties = mapContent.properties.copy(
                        isMyLocationEnabled = mapContent.userLocation != null,
                        mapStyleOptions = if (mapTopAppBar.isNightVision) {
                            null
                        } else MapStyleOptions(MapStyle.json),
                        mapType = if (mapTopAppBar.isNightVision) {
                            MapType.TERRAIN
                        } else MapType.NORMAL
                    )
                )
            } else mapContent

        )
    }

    fun updateZoomLocation(
        mapUi: MapUi,
        newLocation: LatLng
    ): MapUi {
        val mapContent = mapUi.mapContent

        return if (mapContent is MapContentUi.MapContent) {
            mapUi.copy(
                mapContent = mapContent.copy(
                    zoomLocation = newLocation
                )
            )
        } else mapUi
    }

    fun updateDeviceLocation(
        mapUi: MapUi,
        newLocation: LatLng
    ): MapUi {
        val mapContent = mapUi.mapContent

        return if (mapContent is MapContentUi.MapContent) {
            mapUi.copy(
                mapContent = mapContent.copy(
                    properties = mapContent.properties.copy(
                        isMyLocationEnabled = mapContent.userLocation != null,
                    ),
                    userLocation = newLocation
                )
            )
        } else mapUi
    }

    fun updateUiMarkers(
        mapUi: MapUi,
        markersUi: List<MarkerUi>
    ): MapUi {
        val mapContent = mapUi.mapContent

        return if (mapContent is MapContentUi.MapContent) {
            mapUi.copy(
                mapContent = mapContent.copy(
                    markers = markersUi
                )
            )
        } else mapUi
    }

    fun mapMarkerEntityToMarkerUi(
        markerEntity: MarkerEntity
    ): MarkerUi {
        return MarkerUi(
            id = markerEntity.id,
            friendly = markerEntity.friendly,
            lat = markerEntity.lat,
            lng = markerEntity.lng,
        )
    }

    private fun mapMarkerDtoToMarkerUi(
        markerDto: MarkerDto
    ): MarkerUi {
        return MarkerUi(
            id = markerDto.id,
            friendly = markerDto.friendly,
            lat = markerDto.lat,
            lng = markerDto.lng,
        )
    }

    private fun mapDtoMarkers(
        markersDto: MarkerWrapperDto
    ): List<MarkerUi> {
        return markersDto.markersDto.map { dtoItem ->
            mapMarkerDtoToMarkerUi(dtoItem)
        }
    }

    fun mapMarkerUiToMarkerEntity(
        markerUi: MarkerUi
    ): MarkerEntity {
        return MarkerEntity(
            id = markerUi.id,
            friendly = markerUi.friendly,
            lat = markerUi.lat,
            lng = markerUi.lng,
        )
    }
}