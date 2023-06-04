package com.andresen.libraryRepositories.map.local.db

import kotlinx.coroutines.flow.Flow

class MapLocalRepositoryImpl(
    private val dao: MarkerDao
) : MapLocalRepository {

    override suspend fun insertMarker(marker: MarkerEntity) {
        dao.insertMarker(marker)
    }

    override suspend fun deleteMarker(marker: MarkerEntity) {
        dao.deleteMarker(marker)
    }

    override fun getMarkers(): Flow<List<MarkerEntity>> {
        return dao.getMarkers()
    }
}