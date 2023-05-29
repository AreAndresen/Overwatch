package com.andresen.overwatch.feature_map.repository.data.remote.db

import com.andresen.overwatch.main.helper.OverwatchDispatchers
import com.andresen.overwatch.main.helper.network.DataResult
import com.andresen.overwatch.main.helper.network.RequestHelper
import kotlinx.coroutines.withContext

class MapRepository(
    private val api: MapApiService,
    private val requestHelper: RequestHelper,
    private val dispatchers: OverwatchDispatchers,
) {

    suspend fun getFriendlies(): DataResult<out FriendlyTargetWrapperDto> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {

                // mock friendlies at Oslo S
                FriendlyTargetWrapperDto(
                    friendlies = listOf(
                        FriendlyTargetDto(
                            id = 1,
                            friendly = true,
                            lat = 59.910814436867405,
                            lng = 10.752501860260963
                        ),
                        FriendlyTargetDto(
                            id = 2,
                            friendly = true,
                            lat = 59.907246370505554,
                            lng = 10.762107521295547
                        )

                    )
                )

                //api.getFriendlies()
            }
        }
}