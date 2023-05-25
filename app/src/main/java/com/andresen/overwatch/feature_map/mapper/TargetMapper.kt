package com.andresen.overwatch.feature_map.mapper

import com.andresen.overwatch.feature_map.repository.data.local.db.TargetEntity
import com.andresen.overwatch.feature_map.model.TargetUi

object TargetMapper {

    fun targetEntityToTargetUi(
        target: TargetEntity
    ): TargetUi {
        return TargetUi(
            lat = target.lat,
            lng = target.lng,
            id = target.id
        )
    }

    fun targetUiToTargetEntity(
        target: TargetUi
    ): TargetEntity {
        return TargetEntity(
            lat = target.lat,
            lng = target.lng,
            id = target.id
        )
    }
}