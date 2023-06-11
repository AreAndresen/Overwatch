package com.andresen.featureUnits.model

import kotlinx.serialization.Serializable


data class UnitsUi(
    val unitTopSearchBar: UnitTopSearchBar,
    val unitsContent: UnitsContentUi
)

sealed interface UnitsContentUi {
    object Loading : UnitsContentUi
    object Error : UnitsContentUi
    data class Success(
        //val query: String,
        val units: List<UnitUiModel>
    ) : UnitsContentUi
}

@Serializable
data class UnitUiModel(
    val id: String,
    val imgSrc: String?
)

data class UnitTopSearchBar(
    val query: String
)
