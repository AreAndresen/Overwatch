package com.andresen.overwatch.feature_map.repository.data.remote.db

import com.andresen.overwatch.feature_map.model.MarkerUi
import com.andresen.overwatch.main.helper.OverwatchDispatchers
import com.andresen.overwatch.main.helper.network.DataResult
import com.andresen.overwatch.main.helper.network.RequestHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface MapRepository {

    suspend fun insertMarker(marker: MarkerUi)

    suspend fun deleteMarker(marker: MarkerUi)

    suspend fun getMarkersDto(): DataResult<out MarkerWrapperDto>
}