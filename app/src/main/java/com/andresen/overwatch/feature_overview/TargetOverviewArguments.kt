package com.andresen.feature_overview

import java.io.Serializable

data class TargetOverviewArguments(
    val dateTime: String,
    val lng: String? = null,
    val lat: String? = null
) : Serializable