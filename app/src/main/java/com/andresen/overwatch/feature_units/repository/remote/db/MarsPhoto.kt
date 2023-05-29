package com.andresen.overwatch.feature_units.repository.remote.db

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class UnitsWrapperDto(
     val units: List<MarsPhoto>
)

@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
