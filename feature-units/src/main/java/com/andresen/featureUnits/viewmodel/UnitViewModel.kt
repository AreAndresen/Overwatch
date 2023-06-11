package com.andresen.featureUnits.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.featureUnits.mapper.UnitsMapper
import com.andresen.featureUnits.model.UnitsUi
import com.andresen.libraryRepositories.helper.network.DataResult
import com.andresen.libraryRepositories.units.remote.UnitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UnitViewModel(
    // todo rename units
    private val unitRepository: UnitRepository
) : ViewModel() {

    private val mutableUnitsState = MutableStateFlow(UnitsMapper.loading())
    val state: StateFlow<UnitsUi> = mutableUnitsState

    init {
        createUnits()
    }

    private fun createUnits() {
        viewModelScope.launch {
            when (val unitsResult =
                unitRepository.getUnits()) { // unitRepository.getMockUnits()
                is DataResult.Success -> {
                    val unitsDto = unitsResult.data

                    mutableUnitsState.value = UnitsMapper.createUnitsContent(
                        units = unitsDto,
                    )
                }

                is DataResult.Error.AppError -> mutableUnitsState.value = UnitsMapper.error()
                is DataResult.Error.NoNetwork -> mutableUnitsState.value = UnitsMapper.error()
            }

        }
    }

    fun onSearchClick(search: String) {
        mutableUnitsState.update { state ->
            if (search.isNotEmpty()) {
                UnitsMapper.applySearchQuery(state, search)
                UnitsMapper.applySearchQueryResult(state, search)

            } else {
                state
            }
        }
    }

    fun onClearSearch() {
        viewModelScope.launch {
            mutableUnitsState.update { state ->
                when (val unitsResult = unitRepository.getUnits()) {
                    is DataResult.Success -> {
                        val unitsDto = unitsResult.data

                        UnitsMapper.emptySearch(
                            state = state,
                            units = unitsDto,
                        )
                    }

                    is DataResult.Error.AppError -> UnitsMapper.error()
                    is DataResult.Error.NoNetwork -> UnitsMapper.error()
                }

            }
        }
    }
}