package com.andresen.overwatch.feature_map.mapper

import android.location.Location
import com.andresen.overwatch.composable.theme.MapStyle
import com.andresen.overwatch.feature_map.model.MapContentUi
import com.andresen.overwatch.feature_map.model.MapTopAppBar
import com.andresen.overwatch.feature_map.model.MapUi
import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetEntity
import com.andresen.overwatch.feature_map.repository.data.remote.db.FriendlyTargetDto
import com.andresen.overwatch.feature_map.repository.data.remote.db.FriendlyTargetWrapperDto
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
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
        targets: List<TargetUi>,
        friendlies: FriendlyTargetWrapperDto,
        zoomLocation: LatLng,
        userLocation: Location? = null,
    ): MapUi {
        return MapUi(
            mapTopAppBar = MapTopAppBar(
                isNightVision = false
            ),
            mapContent = MapContentUi.MapContent(
                userLocation = if (userLocation != null) {
                    LatLng(userLocation.latitude, userLocation.longitude)
                } else null,
                zoomLocation = zoomLocation,
                targets = targets,
                friendlies = mapFriendlies(friendlies)
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
                    userLocation = newLocation
                )
            )
        } else mapUi
    }

    fun updateDeleteTargetMarkers(
        mapUi: MapUi,
        targets: List<TargetUi>
    ): MapUi {
        val mapContent = mapUi.mapContent

        return if (mapContent is MapContentUi.MapContent) {
            mapUi.copy(
                mapContent = mapContent.copy(
                    targets = targets
                )
            )
        } else mapUi
    }

    fun updateInsertTargetMarkers(
        mapUi: MapUi,
        targets: List<TargetUi>
    ): MapUi {
        val mapContent = mapUi.mapContent

        return if (mapContent is MapContentUi.MapContent) {
            mapUi.copy(
                mapContent = mapContent.copy(
                    targets = targets
                )
            )
        } else mapUi
    }

    fun mapTargetEntityToTargetUi(
        target: TargetEntity
    ): TargetUi {
        return TargetUi(
            id = target.id,
            friendly = target.friendly,
            lat = target.lat,
            lng = target.lng,
        )
    }

    private fun mapTargetDtoToTargetUi(
        target: FriendlyTargetDto
    ): TargetUi {
        return TargetUi(
            id = target.id,
            friendly = target.friendly,
            lat = target.lat,
            lng = target.lng,
        )
    }

    fun mapFriendlies(
        friendlies: FriendlyTargetWrapperDto
    ): List<TargetUi> {
        return friendlies.friendlies.map { dtoItem ->
            mapTargetDtoToTargetUi(dtoItem)
        }
    }

    fun mapTargetUiToTargetEntity(
        target: TargetUi
    ): TargetEntity {
        return TargetEntity(
            id = target.id,
            friendly = target.friendly,
            lat = target.lat,
            lng = target.lng,
        )
    }
}