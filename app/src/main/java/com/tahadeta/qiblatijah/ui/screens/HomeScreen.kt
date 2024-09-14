package com.tahadeta.qiblatijah.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.components.QiblaCompass
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.utils.compassUtils.getDirectionsLabel

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
                        .size(40.dp)
                        .offset(0.dp,(20).dp)
                        .clickable {
                            onMenuClick()
                        },
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        }
    ) { padding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            compassComposable()

            /*
             if (!isMagneticFieldSensorPresent) {
                 CustomDialogAlert(
                     text = stringResource(R.string.missing_sensor_error)
                 )
             }
             */

        }
    }
}


@Composable
@Preview(showBackground = true)
fun CompassAnimationPreview() {
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