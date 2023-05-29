package com.andresen.overwatch.feature_units.mapper

import com.andresen.overwatch.feature_units.model.UnitUiModel
import com.andresen.overwatch.feature_units.model.UnitsContentUi
import com.andresen.overwatch.feature_units.model.UnitsUi
import com.andresen.overwatch.feature_units.repository.remote.db.MarsPhoto

object UnitsMapper {

    fun loading(): UnitsUi = UnitsUi(
        unitsContent = UnitsContentUi.Loading
    )

    fun error(): UnitsUi = UnitsUi(
        unitsContent = UnitsContentUi.Error
    )


    fun createUnitsContent(
        units: List<MarsPhoto>//UnitsWrapperDto,
    ): UnitsUi {
        return UnitsUi(
            unitsContent = UnitsContentUi.Success(
                units = mapUnits(units)
            )
        )
    }

    private fun mapTargetDtoToTargetUi(
        unit: MarsPhoto
    ): UnitUiModel {
        return UnitUiModel(
            id = unit.id,
            imgSrc = unit.imgSrc
        )
    }

    fun mapUnits(
        units: List<MarsPhoto>//UnitsWrapperDto,
    ): List<UnitUiModel> {
        return units.map { dtoItem ->
            mapTargetDtoToTargetUi(dtoItem)
        }
    }

}