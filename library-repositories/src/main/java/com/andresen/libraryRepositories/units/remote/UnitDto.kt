package com.andresen.libraryRepositories.units.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UnitDto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String
)
