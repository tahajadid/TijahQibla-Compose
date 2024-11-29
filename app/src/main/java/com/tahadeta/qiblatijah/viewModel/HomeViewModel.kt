package com.tahadeta.qiblatijah.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(CompassUiState())
    val uiState: StateFlow<CompassUiState> = _uiState.asStateFlow()


    /**
     * function that update the state of location is collected
     */
    fun updateLocationCollected(isLocationCollected: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                isLocationCollected = isLocationCollected
            )
        }
    }

    /**
     * function that update the state of location permission is accepted
     */
    fun updateLocationView(isLocationAccepted: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                isLocationActivated = isLocationAccepted
            )
        }
    }

    /**
     * function that update the state of showing the popup View for update
     */
    fun updateUpdateAppView(showUpdatePopup: Boolean, appUrl: String){
        _uiState.update { currentState ->
            currentState.copy(
                showUpdatePopup = showUpdatePopup,
                appUrl = appUrl
            )
        }
    }
}