package com.tahadeta.qiblatijah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadeta.qiblatijah.ui.components.QiblaCompass
import com.tahadeta.qiblatijah.ui.screens.HomeScreen
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.utils.PreferencesDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QiblaTijahTheme {
                HomeScreen(
                    degrees = 90,
                    isMagneticFieldSensorPresent = true,
                ) {
                    QiblaCompass(
                        degrees = 90,
                        canvasSize = 300.dp
                    )
                }
            }
        }
    }
}