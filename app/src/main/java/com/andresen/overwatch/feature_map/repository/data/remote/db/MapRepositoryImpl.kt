package com.andresen.overwatch.feature_map.repository.data.remote.db

import com.andresen.overwatch.feature_map.model.MarkerUi
import com.andresen.overwatch.main.helper.OverwatchDispatchers
import com.andresen.overwatch.main.helper.network.DataResult
import com.andresen.overwatch.main.helper.network.RequestHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MapRepositoryImpl(
    private val api: MapApiService,
    private val requestHelper: RequestHelper,
    private val dispatchers: OverwatchDispatchers,
) : MapRepository {

    override suspend fun getMarkersDto(): DataResult<out MarkerWrapperDto> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {

                // mock friendlies at Oslo S
                MarkerWrapperDto(
                    markersDto = listOf(
                        MarkerDto(
                            id = 1,
                            friendly = true,
                            lat = 59.910814436867405,
                            lng = 10.752501860260963
                        ),
                        MarkerDto(
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

    override suspend fun insertMarker(marker: MarkerUi) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMarker(marker: MarkerUi) {
        TODO("Not yet implemented")
    }
}