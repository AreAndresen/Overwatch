package com.andresen.libraryRepositories.units.remote

import com.andresen.libraryRepositories.helper.OverwatchDispatchers
import com.andresen.libraryRepositories.helper.network.DataResult
import com.andresen.libraryRepositories.helper.network.RequestHelper
import kotlinx.coroutines.withContext

class UnitRepository(
    private val api: UnitApiService,
    private val requestHelper: RequestHelper,
    private val dispatchers: OverwatchDispatchers,
) {
    suspend fun getUnits(): DataResult<out List<UnitDto>> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {
                api.getUnits()
            }
        }
}