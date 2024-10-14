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

    /*
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    Log.d("TestValue","isLoading (completed) in viewModelScope: "+isLoading.value)
                    _onBoardingCompleted.value = true
                } else {
                    Log.d("TestValue","isLoading (!completed) in viewModelScope: "+isLoading.value)
                    _onBoardingCompleted.value = false
                }
                _isLoading.value = false
            }
        }
    }
     */

    fun updateLocationView(isLocationAccepted: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                isLocationActivated = isLocationAccepted
            )
        }
    }

    fun updateRightAngle(rightAngle: Int){
        _uiState.update { currentState ->
            currentState.copy(
                rightAngle = rightAngle
            )
        }
    }

}