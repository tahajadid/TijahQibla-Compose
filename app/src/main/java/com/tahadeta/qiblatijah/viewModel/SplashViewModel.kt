package com.tahadeta.qiblatijah.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tahadeta.qiblatijah.data.DataStoreRepository
import com.tahadeta.qiblatijah.ui.navigation.Screen
import kotlinx.coroutines.launch
import javax.inject.Inject


class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Home.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect { completed ->
                if (completed) {
                    Log.d("TestValue","isLoading (completed) in viewModelScope: "+isLoading.value)
                    _startDestination.value = Screen.Home.route
                } else {
                    Log.d("TestValue","isLoading (!completed) in viewModelScope: "+isLoading.value)
                    _startDestination.value = Screen.Onboarding.route
                }
                _isLoading.value = false
            }
        }
    }

}