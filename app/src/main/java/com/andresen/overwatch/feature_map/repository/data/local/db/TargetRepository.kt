package com.andresen.overwatch.feature_map.repository.data.local.db

import com.andresen.overwatch.feature_map.model.TargetUi
import kotlinx.coroutines.flow.Flow

interface TargetRepository {

    suspend fun insertTarget(target: TargetUi)

    suspend fun deleteTarget(target: TargetUi)

    fun getTargets(): Flow<List<TargetUi>>
}