package com.tahadeta.qiblatijah.ui.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen(route = "welcome_screen")
    object Home : Screen(route = "home_screen")
    object Settings : Screen(route = "settings_screen")
}