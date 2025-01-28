package com.tahadeta.qiblatijah.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.components.LocationRequestSectionHolder
import com.tahadeta.qiblatijah.ui.components.UpdateAppDialog
import com.tahadeta.qiblatijah.ui.components.customDialog.CustomDialogAlert
import com.tahadeta.qiblatijah.ui.components.compass.QiblaCompass
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgOpacityColor
import com.tahadeta.qiblatijah.utils.firebaseUtils.FirestoreUtil.FetchVersionFromFirestore
import com.tahadeta.qiblatijah.utils.compassUtils.calculateBearing
import com.tahadeta.qiblatijah.utils.compassUtils.getTheRightImage
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtil.getLocationDevice
import com.tahadeta.qiblatijah.utils.userLatitude
import com.tahadeta.qiblatijah.utils.userLongitude
import com.tahadeta.qiblatijah.viewModel.HomeViewModel

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    degrees: Int,
    isMagneticFieldSensorPresent: Boolean,
    homeViewModel: HomeViewModel = viewModel(),
    onMenuClick: () -> Unit = {},
    onOnboardingClick: () -> Unit = {}
) {

    val homeUiState by homeViewModel.uiState.collectAsState()
    val openAlertDialog = remember { mutableStateOf(false) }

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
                        .weight(0.3F)
                        .padding(24.dp)
                        .size(32.dp)
                        .offset(-20.dp, (30).dp)
                        .clickable {
                            onOnboardingClick()
                        },
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(R.string.menu),
                )
                Spacer(modifier = Modifier.weight(0.4F))
                Icon(
                    modifier = Modifier
                        .weight(0.3F)
                        .padding(24.dp)
                        .size(32.dp)
                        .offset(20.dp, (30).dp)
                        .clickable {
                            onMenuClick()
                        },
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.menu),
                )
            }
        }
    ) { padding ->


        // Linear Progress Indicator when the location is collecting
        Box(modifier = modifier
            .fillMaxSize())
        {

            // if the location is activated & coordinates and are fetched
            if(!homeUiState.isLocationCollected && homeUiState.isLocationActivated){
                Column(modifier = modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    // Loader inside Compass for location
                    LoadingLocationInsideCompass()
                }
            }


            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // get current user location
                getLocationDevice(homeViewModel)

                // calculating the right kaaba angle
                val pointerInitDegree = calculateBearing(userLatitude?:0.0,userLongitude?:0.0).toInt()

                // in case of getting Lat & Long of the user we show the clear Compass
                QiblaCompass(
                    degrees = degrees,
                    pointerInitDegree = pointerInitDegree,
                    imageSrc = if(homeUiState.isLocationCollected)
                        getTheRightImage(degrees, pointerInitDegree) else R.drawable.default_compass_bg,
                    rotateCompass = homeUiState.isLocationActivated,
                    isLocationCollected = homeUiState.isLocationCollected
                )

                // in case of non existing of MagneticFieldSensor we should show a dialog alert
                if (!isMagneticFieldSensorPresent) {
                    CustomDialogAlert(
                        text = stringResource(R.string.missing_sensor_error),
                        imageSrc = R.drawable.sad_icon
                    )
                }
            }


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
            }

            // check for version in store
            FetchVersionFromFirestore(homeViewModel)

            if(homeUiState.showUpdatePopup){
                Column(modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)) {

                    if(openAlertDialog.value.equals(false)) {
                        val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(homeUiState.appUrl)) }

                        UpdateAppDialog(
                            onDismissRequest = {
                                openAlertDialog.value = true
                            },
                            onConfirmation = {
                                MainActivity.activityInstance.startActivity(intent)
                                openAlertDialog.value = true
                            },
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun LoadingLocationInsideCompass() {
    Text(text = "Prendre la localisation..")

    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        color = Color.Black,
        trackColor = Color.Gray,
        )
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    QiblaTijahTheme {
        HomeScreen(
            modifier = Modifier,
            20,
            true,
            HomeViewModel(),
            {}
        )
    }
}