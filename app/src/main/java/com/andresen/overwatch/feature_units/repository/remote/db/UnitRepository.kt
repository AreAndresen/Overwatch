package com.andresen.overwatch.feature_units.repository.remote.db

import com.andresen.overwatch.main.helper.OverwatchDispatchers
import com.andresen.overwatch.main.helper.network.DataResult
import com.andresen.overwatch.main.helper.network.RequestHelper
import kotlinx.coroutines.withContext

class UnitRepository(
    private val api: UnitApiService,
    private val requestHelper: RequestHelper,
    private val dispatchers: OverwatchDispatchers,
) {
    //suspend fun getUnits(): List<UnitDto> = api.getUnits()

    suspend fun getUnits(): DataResult<out List<MarsPhoto>> = // UnitsWrapperDto
        withContext(dispatchers.io) {
            requestHelper.tryRequest {
                api.getUnits()
            }
        }
}