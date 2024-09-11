package com.tahadeta.qiblatijah.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.components.CustomDialogAlert
import com.tahadeta.qiblatijah.ui.components.MinimalCompass
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.utils.compressUtils.getDirectionsLabel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    degrees: Int,
    isMagneticFieldSensorPresent: Boolean,
    onMenuClick: () -> Unit = {},
    compassComposable: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    modifier = Modifier.padding(16.dp)
                        .clickable {
                            onMenuClick()
                        },
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.menu)
                )
            }
        }
    ) { padding ->
        Surface {
            Column(
                modifier = modifier.fillMaxSize().padding(padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = getDirectionsLabel(LocalContext.current, degrees),
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h2
                )

                compassComposable()

                if (!isMagneticFieldSensorPresent) {
                    CustomDialogAlert(
                        text = stringResource(R.string.missing_sensor_error)
                    )
                }

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CompassAnimationPreview() {
    QiblaTijahTheme {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            20,
            true,
            {},
            {
                MinimalCompass(
                    degrees = 20,
                    canvasSize = 300.dp
                )
            }
        )
    }
}