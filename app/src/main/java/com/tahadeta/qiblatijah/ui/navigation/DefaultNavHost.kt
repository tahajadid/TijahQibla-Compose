package com.tahadeta.qiblatijah.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.ui.components.compass.QiblaCompass
import com.tahadeta.qiblatijah.ui.screens.HomeScreen
import com.tahadeta.qiblatijah.ui.screens.OnBoardingScreen
import com.tahadeta.qiblatijah.ui.screens.SettingsScreen
import com.tahadeta.qiblatijah.utils.PreferencesDataStore
import com.tahadeta.qiblatijah.utils.compassUtils.getTheRightImage
import com.tahadeta.qiblatijah.utils.languageUtils.changeLanguage
import com.tahadeta.qiblatijah.viewModel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun DefaultNavHost(
    modifier: Modifier = Modifier,
    degrees: Int,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ScreenRoutes.Home.name,
    isMagneticFieldSensorPresent: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ){

        composable(ScreenRoutes.Home.name){
            HomeScreen(
                degrees = degrees,
                isMagneticFieldSensorPresent = isMagneticFieldSensorPresent,
                onMenuClick = {
                    navController.navigate(ScreenRoutes.Settings.name)
                }
            )
        }

        composable(ScreenRoutes.Onboarding.name){
            val dataStore = PreferencesDataStore(LocalContext.current)
            val coroutineScope = rememberCoroutineScope()

            OnBoardingScreen(
                onStartClick = {
                    coroutineScope.launch {
                        dataStore.saveOnboardingPassed(true)
                    }
                    navController.popBackStack()
                    navController.navigate(ScreenRoutes.Home.name)
                },
            )
        }

        composable(ScreenRoutes.Settings.name){
            val dataStore = PreferencesDataStore(LocalContext.current)

            val coroutineScope = rememberCoroutineScope()

            SettingsScreen(
                onArrowBackClick = { navController.popBackStack() },
                onWidgetChoose = { name ->
                    coroutineScope.launch {
                        dataStore.saveWidgetName(name)
                    }
                    navController.popBackStack()
                },
                onLanguageSwitchClick = {
                    changeLanguage(MainActivity.activityInstance,"ar")
                    navController.popBackStack()
                }
            )
        }

    }
}
