package com.andresen.featureUnits.mapper

import com.andresen.libraryRepositories.units.remote.UnitDto
import com.andresen.featureUnits.model.UnitUiModel
import com.andresen.featureUnits.model.UnitsContentUi
import com.andresen.featureUnits.model.UnitsUi

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