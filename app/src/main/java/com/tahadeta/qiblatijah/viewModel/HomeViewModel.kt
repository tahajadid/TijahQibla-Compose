package com.tahadeta.qiblatijah.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadeta.qiblatijah.data.DataStoreRepository
import com.tahadeta.qiblatijah.ui.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel : ViewModel() {

    // UI state
    private val _uiState = MutableStateFlow(CompassUiState())
    val uiState: StateFlow<CompassUiState> = _uiState.asStateFlow()

    /*
    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _onBoardingCompleted: MutableState<Boolean> = mutableStateOf(false)
    val onBoardingCompleted: State<Boolean> = _onBoardingCompleted

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

    fun updateCompletedOnboarding(isCompleted: Boolean){
        _uiState.update { currentState ->
            currentState.copy(
                isOnboardingCompleted = isCompleted
            )
        }
    }
}