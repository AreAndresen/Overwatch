package com.andresen.overwatch.feature_map.repository.data.local.db

import com.andresen.overwatch.feature_map.mapper.MapMapper
import com.andresen.overwatch.feature_map.model.TargetUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TargetRepositoryImpl(
    private val dao: TargetDao
): TargetRepository {

    override suspend fun insertTarget(target: TargetUi) {
        dao.insertTarget(MapMapper.mapTargetUiToTargetEntity(target))
    }

    override suspend fun deleteTarget(target: TargetUi) {
        dao.deleteTarget(MapMapper.mapTargetUiToTargetEntity(target))
    }

    override fun getTargets(): Flow<List<TargetUi>> {
        return dao.getTargets().map { target ->
            target.map {
                MapMapper.mapTargetEntityToTargetUi(it)
            }
        }
    }
}