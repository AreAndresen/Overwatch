package com.andresen.overwatch.feature_overview.mapper

import com.andresen.overwatch.feature_overview.repository.data.local.db.TargetEntity
import com.andresen.overwatch.feature_overview.model.TargetUi

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