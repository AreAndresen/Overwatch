package com.andresen.featureUnits.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresen.libraryRepositories.units.remote.UnitRepository
import com.andresen.featureUnits.mapper.UnitsMapper
import com.andresen.featureUnits.model.UnitsUi
import com.andresen.libraryRepositories.helper.network.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UnitViewModel( // todo rename units
    private val unitRepository: UnitRepository,
    private val context: Context,
) : ViewModel() {

    private val mutableUnitsState = MutableStateFlow(UnitsMapper.loading())
    val state: StateFlow<UnitsUi> = mutableUnitsState

    init {
        createUnits()
    }

    private fun createUnits() {
        viewModelScope.launch {
            when (val unitsResult = unitRepository.getMockUnits(context)) { // unitRepository.getUnits()
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