package com.andresen.overwatch.feature_map.repository.data.local.db

import com.andresen.overwatch.feature_map.mapper.MapMapper
import com.andresen.overwatch.feature_map.model.MarkerUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MapLocalRepositoryImpl(
    private val dao: MarkerDao
): MapLocalRepository {

    override suspend fun insertMarker(marker: MarkerUi) {
        dao.insertMarker(MapMapper.mapMarkerUiToMarkerEntity(marker))
    }

    override suspend fun deleteMarker(marker: MarkerUi) {
        dao.deleteMarker(MapMapper.mapMarkerUiToMarkerEntity(marker))
    }

    override fun getMarkers(): Flow<List<MarkerUi>> {
        return dao.getMarkers().map { marker ->
            marker.map {
                MapMapper.mapMarkerEntityToMarkerUi(it)
            }
        }
    }
}