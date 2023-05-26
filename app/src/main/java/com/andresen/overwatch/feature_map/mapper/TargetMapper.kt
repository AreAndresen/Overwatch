package com.andresen.overwatch.feature_map.mapper

import com.andresen.overwatch.feature_map.model.TargetUi
import com.andresen.overwatch.feature_map.repository.data.local.db.TargetEntity
import com.andresen.overwatch.feature_map.repository.data.remote.db.FriendlyTargetDto
import com.andresen.overwatch.feature_map.repository.data.remote.db.FriendlyTargetWrapperDto

object TargetMapper {

    fun targetEntityToTargetUi(
        target: TargetEntity
    ): TargetUi {
        return TargetUi(
            id = target.id,
            friendly = target.friendly,
            lat = target.lat,
            lng = target.lng,
        )
    }

    private fun targetDtoToTargetUi(
        target: FriendlyTargetDto
    ): TargetUi {
        return TargetUi(
            id = target.id,
            friendly = target.friendly,
            lat = target.lat,
            lng = target.lng,
        )
    }

    fun mapFriendlies(
        friendlies: FriendlyTargetWrapperDto
    ): List<TargetUi> {
        return friendlies.friendlies.map { dtoItem ->
            targetDtoToTargetUi(dtoItem)
        }
    }

    fun targetUiToTargetEntity(
        target: TargetUi
    ): TargetEntity {
        return TargetEntity(
            id = target.id,
            friendly = target.friendly,
            lat = target.lat,
            lng = target.lng,
        )
    }
}