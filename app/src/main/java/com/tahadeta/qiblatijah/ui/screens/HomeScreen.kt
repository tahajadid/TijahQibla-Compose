package com.tahadeta.qiblatijah.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.components.LocationRequestSectionHolder
import com.tahadeta.qiblatijah.ui.components.customDialog.CustomDialogAlert
import com.tahadeta.qiblatijah.ui.components.compass.QiblaCompass
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgOpacityColor
import com.tahadeta.qiblatijah.utils.PreferencesDataStore
import com.tahadeta.qiblatijah.utils.compassUtils.calculateBearing
import com.tahadeta.qiblatijah.utils.compassUtils.getTheRightImage
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtil.getLocationDevice
import com.tahadeta.qiblatijah.utils.userLatitude
import com.tahadeta.qiblatijah.utils.userLongitude
import com.tahadeta.qiblatijah.viewModel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    degrees: Int,
    pointerInitDegree: Int,
    isMagneticFieldSensorPresent: Boolean,
    homeViewModel: HomeViewModel = viewModel(),
    onMenuClick: () -> Unit = {},
) {

    val homeUiState by homeViewModel.uiState.collectAsState()


    Scaffold(
        backgroundColor = ScreenBgColor,
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(ScreenBgColor),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier
                        .padding(24.dp)
                        .size(32.dp)
                        .offset(0.dp, (20).dp)
                        .clickable {
                            onMenuClick()
                        },
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        }
    ) { padding ->

        Box(modifier = modifier
            .fillMaxSize())
        {

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // check for the location
                //LocationUtils.requestLocation()

                val pointerInitDegree = calculateBearing(userLatitude?:0.0,userLongitude?:0.0).toInt()

                Log.d("TestpointerInitDegree","pointerInitDegree : "+ pointerInitDegree)
                QiblaCompass(
                    degrees = degrees,
                    pointerInitDegree = pointerInitDegree,
                    imageSrc = getTheRightImage(degrees, pointerInitDegree),
                    rotateCompass = homeUiState.isLocationActivated,
                )

                // in case of non existing of MagneticFieldSensor we should show a dialog alert
                if (!isMagneticFieldSensorPresent) {
                    CustomDialogAlert(
                        text = stringResource(R.string.missing_sensor_error),
                        imageSrc = R.drawable.sad_icon
                    )
                }


            }


            //showLocationDisabledSection = LocationUtils.areLocationPermissionsGranted()
            Log.d("TestTT","homeUiState.isLocationActivated :"
                    + homeUiState.isLocationActivated)

            if(!(homeUiState.isLocationActivated)){
                // in case of the user does not give the app the location access
                // a section should be appear to remind him that the compass cannot work
                // and give the right angle without latitude and longitude
                LocationRequestSectionHolder(
                    modifier
                        .align(Alignment.Center)
                        .padding(bottom = 40.dp)
                        .background(ScreenBgOpacityColor)
                )

                // LocationUtils.requestLocation(homeViewModel)
            }


            getLocationDevice(homeViewModel)

        }
    }
}




@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    QiblaTijahTheme {
        HomeScreen(
            modifier = Modifier,
            20,
            20,
            true,
            HomeViewModel(),
            {}
        )
    }
}