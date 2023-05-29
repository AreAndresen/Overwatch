package com.andresen.overwatch.feature_units.repository.remote.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UnitDto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
