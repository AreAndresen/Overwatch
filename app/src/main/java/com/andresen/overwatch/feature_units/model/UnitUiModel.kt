package com.andresen.overwatch.feature_units.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnitUiModel(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
