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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.ui.screens.HomeScreen
import com.tahadeta.qiblatijah.ui.screens.OnBoardingScreen
import com.tahadeta.qiblatijah.ui.screens.SettingsScreen
import com.tahadeta.qiblatijah.utils.PreferencesDataStore
import com.tahadeta.qiblatijah.utils.languageUtils.changeLanguage
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun DefaultNavHost(
    modifier: Modifier = Modifier,
    degrees: Int,
    pointerInitDegree: Int,
    navController: NavHostController,
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
                pointerInitDegree = pointerInitDegree,
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
                navController
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
