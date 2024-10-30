package com.tahadeta.qiblatijah.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
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
    navController: NavHostController,
    startDestination: String = Screen.Home.route,
    isMagneticFieldSensorPresent: Boolean
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ){


        composable(route = Screen.Onboarding.route){
            val dataStore = PreferencesDataStore(LocalContext.current)
            val coroutineScope = rememberCoroutineScope()

            OnBoardingScreen(
                onStartClick = {
                    /*
                    coroutineScope.launch {
                        dataStore.saveOnboardingPassed(true)
                    }

                     */
                    navController.popBackStack()
                    navController.navigate(Screen.Home.route)
                },
                navController
            )
        }


        composable(route = Screen.Home.route){
            HomeScreen(
                degrees = degrees,
                isMagneticFieldSensorPresent = isMagneticFieldSensorPresent,
                onMenuClick = {
                    navController.navigate(Screen.Settings.route)
                },
                onOnboardingClick = {
                    navController.navigate(Screen.Onboarding.route)
                }
            )
        }


        composable(route = Screen.Settings.route){
            SettingsScreen(
                onArrowBackClick = { navController.popBackStack() },
                onLanguageSwitchClick = {
                    changeLanguage(MainActivity.activityInstance,"ar")
                    navController.popBackStack()
                }
            )
        }

    }
}
