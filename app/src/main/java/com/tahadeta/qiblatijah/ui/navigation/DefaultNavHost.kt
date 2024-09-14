package com.tahadeta.qiblatijah.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tahadeta.qiblatijah.ui.components.QiblaCompass
import com.tahadeta.qiblatijah.ui.screens.HomeScreen
import com.tahadeta.qiblatijah.utils.PreferencesDataStore

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
                onMenuClick = { navController.navigate(ScreenRoutes.Settings.name )}
            ) {
                val dataStore = PreferencesDataStore(LocalContext.current)
                val selectedWidget by dataStore.getWidgetName.collectAsState(initial = null)

                QiblaCompass(
                    degrees = degrees,
                )

                /*
                when(selectedWidget){
                    Widgets.QiblaCompass.name ->
                        QiblaCompass(
                            degrees = degrees,
                            canvasSize = 300.dp
                        )
                    else -> StyledCompass(
                        degrees = degrees,
                        size = 300.dp
                    )
                }

                 */
            }
        }



        /*
        composable(ScreenRoutes.WidgetSelection.name){
            val dataStore = PreferencesDataStore(LocalContext.current)

            val coroutineScope = rememberCoroutineScope()

            WidgetScreen(
                onArrowBackClick = { navController.popBackStack() },
                onWidgetChoose = { name ->
                    coroutineScope.launch {
                        dataStore.saveWidgetName(name)
                    }

                    navController.popBackStack()
                }
            )
        }

         */
    }
}
