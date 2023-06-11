package com.andresen.libraryRepositories.units.remote

import android.content.Context
import com.andresen.libraryRepositories.helper.OverwatchDispatchers
import com.andresen.libraryRepositories.helper.network.DataResult
import com.andresen.libraryRepositories.helper.network.RequestHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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


    suspend fun getMockUnits(context: Context): DataResult<out List<UnitDto>> =
        withContext(dispatchers.io) {
            requestHelper.tryRequest {

                /*lateinit var jsonString: String
                try {
                    jsonString = context.assets.open("MockResponse.json")
                        .bufferedReader()
                        .use { it.readText() }
                } catch (ioException: IOException) {
                    Timber.e("Failed to get json mock")
                }*/

                val gson = Gson()
                val listUnitsType = object : TypeToken<List<UnitDto>>() {}.type
                val jsonFile = MockResponse.json

                val units: List<UnitDto> = gson.fromJson(jsonFile, listUnitsType)
                units


            }
        }
}