package com.andresen.overwatch.feature_units.model

import kotlinx.serialization.Serializable


data class UnitsUi(
    val unitsContent: UnitsContentUi
)

sealed interface UnitsContentUi {
    object Loading : UnitsContentUi
    object Error : UnitsContentUi
    data class Success(
        val units: List<UnitUiModel>
    ) : UnitsContentUi
}

@Serializable
data class UnitUiModel(
    val id: String,
    val imgSrc: String
)

