package com.andresen.overwatch.feature_map.repository.data.remote.db

/*
class MapRepositoryImpl(
    private val api: MapApiService,
    private val requestHelper: RequestHelper,
    private val dispatchers: OverwatchDispatchers,
    private val mapGlobalEvent: MapGlobalEvent
) : MapRepository {

    override suspend fun getMarkersDto(): DataResult<out MarkerWrapperDto> = withContext(dispatchers.io) {
        requestHelper.tryRequest {

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

            // todo implement real API
            //api.getMarkers()
        }
    }

    override suspend fun insertMarker(marker: MarkerDto): DataResult<out Unit> = withContext(dispatchers.io) {
        requestHelper.tryRequest {

            // todo implement real API
            //api.insertMarker(marker.id, marker)

            withContext(Dispatchers.IO) {
                mapGlobalEvent.mapUpdate()
            }
        }
    }


    override suspend fun deleteMarker(marker: MarkerDto): DataResult<out Unit> = withContext(dispatchers.io) {
        requestHelper.tryRequest {

            // todo implement real API
            //api.deleteMarker(deleteLink)

            withContext(Dispatchers.IO) {
                mapGlobalEvent.mapUpdate()
            }
        }
    }
}*/