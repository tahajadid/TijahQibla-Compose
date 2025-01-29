package com.tahadeta.qiblatijah

import android.content.Context
import android.app.LocaleManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
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
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.tahadeta.qiblatijah.utils.Constants.APP_LINK
import com.tahadeta.qiblatijah.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private val accelerometerReading = FloatArray(3)
    private val magnetometerReading = FloatArray(3)
    private val rotationMatrix = FloatArray(9)
    private val mOrientationAngles = FloatArray(3)

    private val degrees: MutableState<Int> = mutableStateOf(0)


    companion object {
        lateinit var activityInstance: MainActivity
        const val LANGUAGE_INDEX = 0
    }

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("TestValue","isLoading : "+splashViewModel.isLoading.value)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                splashViewModel.isLoading.value
            }
        }

        Log.d("TestValue","isLoading : "+splashViewModel.isLoading.value)


        // update the language
        // changeLanguage(this,"ar")

        //setPerAppLanguage("fr")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val isMagneticFieldSensorPresent =
            sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null

        enableEdgeToEdge()

        activityInstance = this

        setContent {
            QiblaTijahTheme {
                val screen by splashViewModel.startDestination
                val navController = rememberNavController()


                // add lunch effect
                var isNavigating by remember { mutableStateOf(false) }
                LaunchedEffect(splashViewModel.isLoading.value) {
                    if (!splashViewModel.isLoading.value && !isNavigating) {
                        isNavigating = true
                        navController.navigate(screen)
                    }
                }


                DefaultNavHost(
                    navController = navController,
                    degrees = degrees.value,
                    isMagneticFieldSensorPresent = isMagneticFieldSensorPresent,
                    startDestination = screen
                )
            }
        }
    }

    // function that redirect to settings to activate the location access
    fun callSettings(){
        try {
            this.startActivity(
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                )
            )
        } catch (e: Exception) {
            Log.e("NoPermissionDialog", "e:: $e")
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


    /**
     * function that open wikipedia link
     */
    fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        this.startActivity(intent)
    }

    /**
     * function that create an Intent to share the app link
     */
    fun shareAppLink() {
        try {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "TIJAH QIBLA")
            var shareMessage = "\nJe te recommande cette application\n\n"
            val link = APP_LINK
            shareMessage =
                """
                $shareMessage $link
                    """.trimIndent()
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "choose one"))
        } catch (e: Exception) {
            // e.toString();
        }

    }

    /**
     * function that add the app link in clipboard
     */
    fun copyAppLink(){
        val clipboardManager = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", APP_LINK)
        Toast.makeText(this.applicationContext, "text copié", Toast.LENGTH_SHORT).show()
        clipboardManager.setPrimaryClip(clipData)
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