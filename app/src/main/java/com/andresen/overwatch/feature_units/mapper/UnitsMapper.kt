package com.andresen.overwatch.feature_units.mapper

import com.andresen.overwatch.feature_units.model.UnitUiModel
import com.andresen.overwatch.feature_units.model.UnitsContentUi
import com.andresen.overwatch.feature_units.model.UnitsUi
import com.andresen.overwatch.feature_units.repository.remote.db.UnitDto

object UnitsMapper {

    fun loading(): UnitsUi = UnitsUi(
        unitsContent = UnitsContentUi.Loading
    )

    fun error(): UnitsUi = UnitsUi(
        unitsContent = UnitsContentUi.Error
    )


    fun createUnitsContent(
        units: List<UnitDto>
    ): UnitsUi {
        return UnitsUi(
            unitsContent = UnitsContentUi.Success(
                units = mapUnits(units)
            )
        )
    }

    private fun mapUnitDtoToUnitUi(
        unit: UnitDto
    ): UnitUiModel {
        return UnitUiModel(
            id = unit.id,
            imgSrc = unit.imgSrc
        )
    }

    private fun mapUnits(
        units: List<UnitDto>
    ): List<UnitUiModel> {
        return units.map { dtoItem ->
            mapUnitDtoToUnitUi(dtoItem)
        }
    }

}