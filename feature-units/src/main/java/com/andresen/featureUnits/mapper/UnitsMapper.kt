package com.andresen.featureUnits.mapper

import com.andresen.featureUnits.model.UnitTopSearchBar
import com.andresen.featureUnits.model.UnitUiModel
import com.andresen.featureUnits.model.UnitsContentUi
import com.andresen.featureUnits.model.UnitsUi
import com.andresen.libraryRepositories.units.remote.UnitDto

object UnitsMapper {

    fun loading(): UnitsUi = UnitsUi(
        unitTopSearchBar = UnitTopSearchBar(
            query = ""
        ),
        unitsContent = UnitsContentUi.Loading
    )

    fun error(): UnitsUi = UnitsUi(
        unitTopSearchBar = UnitTopSearchBar(
            query = ""
        ),
        unitsContent = UnitsContentUi.Error
    )


    fun createUnitsContent(
        units: List<UnitDto>
    ): UnitsUi {
        return UnitsUi(
            unitTopSearchBar = UnitTopSearchBar(
                query = ""
            ),
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

    fun emptySearch(
        state: UnitsUi,
        units: List<UnitDto>
    ): UnitsUi {
        val unitsContent = state.unitsContent
        val unitTopSearchBar = state.unitTopSearchBar
        return state.copy(
            unitTopSearchBar = unitTopSearchBar.copy(
                query = ""
            ),
            unitsContent = if (unitsContent is UnitsContentUi.Success) {
                unitsContent.copy(
                    units = mapUnits(units)
                )
            } else unitsContent
        )
    }

    fun applySearchQueryResult(
        state: UnitsUi,
        query: String,
    ): UnitsUi {
        val unitsContent = state.unitsContent
        return state.copy(
            unitsContent = if (unitsContent is UnitsContentUi.Success) {
                unitsContent.copy(
                    units = unitsContent.units.filter { unit ->
                        unit.id.contains(query)
                    }
                )
            } else unitsContent
        )
    }

    fun applySearchQuery(
        state: UnitsUi,
        query: String
    ): UnitsUi {
        val unitTopSearchBar = state.unitTopSearchBar

        return state.copy(
            unitTopSearchBar = unitTopSearchBar.copy(
                query = query
            )
        )
    }

}