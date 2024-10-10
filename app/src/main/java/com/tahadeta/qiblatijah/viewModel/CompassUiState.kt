package com.tahadeta.qiblatijah.viewModel

data class CompassUiState(
    val currentAngle: Int = 360,
    val rightAngle: Int = 0,
    val isLocationActivated: Boolean = false,
    val isOnboardingCompleted: Boolean = false
)
