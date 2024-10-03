package com.tahadeta.qiblatijah

import android.Manifest
import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.LocaleListCompat
import com.tahadeta.qiblatijah.ui.navigation.DefaultNavHost
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import android.os.LocaleList
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.tahadeta.qiblatijah.ui.navigation.ScreenRoutes
import com.tahadeta.qiblatijah.utils.PreferencesDataStore
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtil.NoPermissionDialog
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtil.createLocationRequest
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtil.fetchLastLocation
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtils
import com.tahadeta.qiblatijah.viewModel.HomeViewModel
import java.util.Locale
import kotlin.math.roundToInt

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val mOrientationAngles = FloatArray(3)

    private val degrees: MutableState<Int> = mutableStateOf(0)

    // default image
    private val imageRrc: MutableState<Int> = mutableStateOf(R.drawable.default_compass_new)

    companion object {
        lateinit var activityInstance: MainActivity
        const val LANGUAGE_INDEX = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // update the language
        // changeLanguage(this,"ar")

        setPerAppLanguage("ar")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val isMagneticFieldSensorPresent =
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null

        enableEdgeToEdge()

        activityInstance = this

        setContent {
            // pass the composable
            val dataStore = PreferencesDataStore(LocalContext.current)
            val onboardingPassed by dataStore.getOnboardingPassed.collectAsState(initial = true)


            QiblaTijahTheme {
                DefaultNavHost(
                    degrees = degrees.value,
                    isMagneticFieldSensorPresent = isMagneticFieldSensorPresent,
                    startDestination = if(onboardingPassed == true)
                        ScreenRoutes.Home.name
                    else ScreenRoutes.Onboarding.name
                )
            }
        }
    }


    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do something here if sensor accuracy changes.
        // You must implement this callback in your code.
    }


    override fun onResume() {
        super.onResume()

        // Get updates from the accelerometer and magnetometer at a constant rate.
        // To make batch operations more efficient and reduce power consumption,
        // provide support for delaying updates to the application.
        //
        // In this example, the sensor reporting delay is small enough such that
        // the application receives an update before the system checks the sensor
        // readings again.
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                this,
                accelerometer,
                SensorManager.SENSOR_DELAY_GAME,
                SensorManager.SENSOR_DELAY_GAME
            )
        }
        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(
                this,
                magneticField,
                SensorManager.SENSOR_DELAY_GAME,
                SensorManager.SENSOR_DELAY_GAME
            )
        }


    }

    override fun onPause() {
        super.onPause()

        // Don't receive any more updates from either sensor.
        sensorManager.unregisterListener(this)
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading, 0, accelerometerReading.size)
        } else if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading, 0, magnetometerReading.size)
        }
        val azimuthInRadians = this.mOrientationAngles[0]

        val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).roundToInt()

        degrees.value = if(azimuthInDegrees < 0 ) azimuthInDegrees + 360
        else azimuthInDegrees

        updateOrientationAngles()
    }

    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    fun updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(
            rotationMatrix,
            null,
            accelerometerReading,
            magnetometerReading
        )
        SensorManager.getOrientation(rotationMatrix, mOrientationAngles)
    }



    private fun getDeviceLanguage(): String  {
        return LocaleListCompat
            .getDefault()
            .get(LANGUAGE_INDEX)?.language
            ?:
            "fr"
    }

    fun setPerAppLanguage(language: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            with(
                getSystemService(
                    LocaleManager::class.java
                )
            ) {
                val appLang = applicationLocales[LANGUAGE_INDEX]
                if (appLang == null || appLang.toLanguageTag() != language) {
                    applicationLocales =
                        LocaleList(Locale.forLanguageTag(language))
                }
            }
        } else {
            val appLang = AppCompatDelegate.getApplicationLocales()[LANGUAGE_INDEX]
            if (appLang == null || appLang.toLanguageTag() != language) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        language
                    )
                )
            }
        }
    }

}