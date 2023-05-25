package com.andresen.overwatch.feature_map.repository.data.local.db

import com.andresen.overwatch.feature_map.mapper.TargetMapper
import com.andresen.overwatch.feature_map.model.TargetUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TargetRepositoryImpl(
    private val dao: TargetDao
): TargetRepository {

    override suspend fun insertTarget(target: TargetUi) {
        dao.insertTarget(TargetMapper.targetUiToTargetEntity(target))
    }

    override suspend fun deleteTarget(target: TargetUi) {
        dao.deleteTarget(TargetMapper.targetUiToTargetEntity(target))
    }

    override fun getTargets(): Flow<List<TargetUi>> {
        return dao.getTargets().map { target ->
            target.map { TargetMapper.targetEntityToTargetUi(it) }
        }
    }
}