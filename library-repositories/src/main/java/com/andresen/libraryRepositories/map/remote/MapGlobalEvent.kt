package com.andresen.libraryRepositories.map.remote

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow

class MapGlobalEvent {

    private val mapUpdate = BroadcastChannel<String>(1)

    suspend fun mapUpdate() {
        mapUpdate.send("Update")
    }

    fun mapUpdateListener() = mapUpdate.asFlow()
}