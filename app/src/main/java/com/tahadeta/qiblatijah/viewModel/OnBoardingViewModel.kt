package com.tahadeta.qiblatijah.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class OnBoardingViewModel : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(CompassUiState())
    val uiState: StateFlow<CompassUiState> = _uiState.asStateFlow()

    fun updateLocationView(isLocationAccepted: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                isLocationActivated = isLocationAccepted
            )
        }
    }

}