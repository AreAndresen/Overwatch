package com.andresen.overwatch.feature_units.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.overwatch.feature_units.mapper.UnitsMapper
import com.andresen.overwatch.feature_units.model.UnitsUi
import com.andresen.overwatch.feature_units.repository.remote.db.UnitRepository
import com.andresen.overwatch.main.helper.network.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UnitViewModel(
    private val unitRepository: UnitRepository
) : ViewModel() {

    private val mutableUnitsState = MutableStateFlow(UnitsMapper.loading())
    val state: StateFlow<UnitsUi> = mutableUnitsState

    init {
        createUnits()
    }

    private fun createUnits() {
        viewModelScope.launch {
            when (val unitsResult = unitRepository.getUnits()) {
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
}