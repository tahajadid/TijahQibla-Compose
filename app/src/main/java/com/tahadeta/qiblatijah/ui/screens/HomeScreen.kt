package com.tahadeta.qiblatijah.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.components.CustomDialogAlert
import com.tahadeta.qiblatijah.ui.components.QiblaCompass
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgOpacityColor
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtils
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    degrees: Int,
    isMagneticFieldSensorPresent: Boolean,
    onMenuClick: () -> Unit = {},
    compassComposable: @Composable () -> Unit
) {

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

                // pass the composable
                compassComposable()

                // in case of non existing of MagneticFieldSensor we should show a dialog alert
                if (!isMagneticFieldSensorPresent) {
                    CustomDialogAlert(
                        text = stringResource(R.string.missing_sensor_error)
                    )
                }


            }

            AnimateLocationAccess(modifier.align(Alignment.Center))

        }

    }
}

@Composable
fun AnimateLocationAccess(
    modifier: Modifier = Modifier
) {
    var blurIsVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        blurIsVisible = true
        delay(2000) // Delay for 2 secondvisible = true
    }

    var activateLocationIsVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        activateLocationIsVisible = true
        delay(8000) // Delay for 4 secondvisible = true
    }

    AnimatedVisibility(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        visible = blurIsVisible,
        enter =fadeIn(),
        exit = fadeOut()
    ) {
        Box(modifier = Modifier.background(ScreenBgOpacityColor))
    }

    AnimatedVisibility(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth()
            .padding(bottom = 40.dp),
        visible = activateLocationIsVisible,
        enter =fadeIn(),
        exit = fadeOut()
    ) {
        CustomDialogAlert(
            text = stringResource(R.string.missing_sensor_error)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    QiblaTijahTheme {
        HomeScreen(
            modifier = Modifier,
            20,
            true,
            {},
            {
                QiblaCompass(
                    degrees = 93,
                    imageSrc = R.drawable.correct_compass
                )
            }
        )
    }
}