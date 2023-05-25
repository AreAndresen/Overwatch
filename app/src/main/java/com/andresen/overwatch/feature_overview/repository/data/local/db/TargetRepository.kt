package com.andresen.overwatch.feature_overview.repository.data.local.db

import com.andresen.overwatch.feature_overview.model.TargetUi
import kotlinx.coroutines.flow.Flow

interface TargetRepository {

    suspend fun insertTarget(target: TargetUi)

    suspend fun deleteTarget(target: TargetUi)

    fun getTargets(): Flow<List<TargetUi>>
}