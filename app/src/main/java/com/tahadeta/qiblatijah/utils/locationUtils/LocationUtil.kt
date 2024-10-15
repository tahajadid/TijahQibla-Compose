package com.tahadeta.qiblatijah.utils.locationUtils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.ActivityCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.tahadeta.qiblatijah.BuildConfig
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.ColorCorrect
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.utils.userLatitude
import com.tahadeta.qiblatijah.utils.userLongitude
import com.tahadeta.qiblatijah.viewModel.HomeViewModel
import kotlin.coroutines.coroutineContext


object LocationUtil {

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun getLocationDevice(homeViewModel: HomeViewModel){
        var locationFromGps: Location? by remember { mutableStateOf(null) }
        var openDialog: String by remember { mutableStateOf("") }

        val locationPermissionsState = rememberMultiplePermissionsState(
            listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )

        val context = LocalContext.current
        val fusedLocationProviderClient = remember { LocationServices.getFusedLocationProviderClient(context) }
        val locationCallback = remember {
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    userLatitude = locationResult.lastLocation?.latitude
                    userLongitude = locationResult.lastLocation?.longitude
                    homeViewModel.updateLocationCollected(true)

                    Log.d("onLocationResult", "locationResult.latitude: ${locationResult.lastLocation?.latitude}")
                    Log.d("onLocationResult", "locationResult.longitude: ${locationResult.lastLocation?.longitude}")
                    locationFromGps = locationResult.lastLocation
                }
            }
        }


        val settingsLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartIntentSenderForResult(),
            onResult = {
                when (it.resultCode) {
                    Activity.RESULT_OK -> {
                        context.fetchLastLocation(
                            fusedLocationClient = fusedLocationProviderClient,
                            settingsLauncher = null,
                            location = {
                                Log.d("settingsLauncher", "location: ${it.latitude}")
                                if (locationFromGps == null && locationFromGps != it) {
                                    locationFromGps = it
                                }
                            },
                            locationCallback = locationCallback
                        )
                    }
                    Activity.RESULT_CANCELED -> {
                        Toast.makeText(context, "Activity.RESULT_CANCELED", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )

        LaunchedEffect(
            key1 = locationPermissionsState.revokedPermissions.size,
            key2 = locationPermissionsState.shouldShowRationale,
            block = {
                fetchLocation(
                    locationPermissionsState,
                    context,
                    settingsLauncher,
                    fusedLocationProviderClient,
                    locationCallback,
                    homeViewModel,
                    openDialog = {
                        openDialog = it
                    })
            })

        LaunchedEffect(
            key1 = locationFromGps,
            block = {
                Log.d("LaunchedEffect", "locationFromGps: $locationFromGps")
                // TODO: setup GeoCoder

            }
        )

        DisposableEffect(
            key1 = true
        ) {
            onDispose {
                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            }
        }


        if (openDialog.isNotEmpty()) {
            Dialog(
                onDismissRequest = { openDialog = "" },
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                NoPermissionDialog(
                    message = openDialog,
                    reqPermission = {
                        locationPermissionsState.launchMultiplePermissionRequest()
                        openDialog = ""
                    }
                )
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    private fun fetchLocation(
        locationPermissionsState: MultiplePermissionsState,
        context: Context,
        settingsLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
        fusedLocationProviderClient: FusedLocationProviderClient,
        locationCallback: LocationCallback,
        homeViewModel: HomeViewModel,
        openDialog: (String) -> Unit
    ) {
        when {
            locationPermissionsState.revokedPermissions.size <= 1 -> {

                homeViewModel.updateLocationView(true)

                // Has permission at least one permission [coarse or fine]
                context.createLocationRequest(
                    settingsLauncher = settingsLauncher,
                    fusedLocationClient = fusedLocationProviderClient,
                    locationCallback = locationCallback
                )
                Log.d("LaunchedEffect", "revokedPermissions.size <= 1")
            }
            locationPermissionsState.shouldShowRationale -> {
                openDialog("Permettre a sauvegarde")
                Log.d("LaunchedEffect", "shouldShowRationale")
            }
            locationPermissionsState.revokedPermissions.size == 2 -> {
                locationPermissionsState.launchMultiplePermissionRequest()
                Log.d("LaunchedEffect", "revokedPermissions.size == 2")
            }
            else -> {
                openDialog("Cette application nécessite une autorisation de localisation")
                Log.d("LaunchedEffect", "else")
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun Context.fetchLastLocation(
        fusedLocationClient: FusedLocationProviderClient,
        settingsLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>?,
        location: (Location) -> Unit,
        locationCallback: LocationCallback
    ) {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                location(it)
            } else {
                this.createLocationRequest(
                    settingsLauncher = settingsLauncher,
                    fusedLocationClient = fusedLocationClient,
                    locationCallback = locationCallback
                )
            }
        }
    }

    @SuppressLint("MissingPermission", "LongLogTag")
    fun Context.createLocationRequest(
        settingsLauncher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>?,
        fusedLocationClient: FusedLocationProviderClient,
        locationCallback: LocationCallback
    ) {

        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = 1 * 1000
            isWaitForAccurateLocation = true

        }
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                mainLooper
            )
        }

        task.addOnFailureListener { exception ->
            Log.e("LocationUtil.createLocationRequest", exception.toString())
            if (exception is ResolvableApiException) {
                try {
                    settingsLauncher?.launch(
                        IntentSenderRequest.Builder(exception.resolution).build()
                    )
                } catch (e: Exception) {
                    // Ignore the error.
                }
            }
        }
    }


    @Composable
    fun NoPermissionDialog(reqPermission: () -> Unit, message: String) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
            elevation = 8.dp
        ) {
            val context = LocalContext.current

            Column(
                Modifier.padding(16.dp)
            ) {
                Text(text = message)
                Spacer(modifier = Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.align(
                    Alignment.End)) {

                    val location_refused_title = stringResource(id = R.string.location_refused_title)

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ColorCorrect,
                            contentColor = ScreenBgColor
                        ),
                        onClick = {
                        if (message == location_refused_title) {
                            try {
                                context.startActivity(
                                    Intent(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.parse("package:" + BuildConfig.APPLICATION_ID)
                                    )
                                )
                            } catch (e: Exception) {
                                Log.e("NoPermissionDialog", "e:: $e")
                            }
                        } else {
                            reqPermission()
                        }
                    }) {
                        Text(text = if (message == location_refused_title)
                            stringResource(id = R.string.location_refused_parametrs) else
                                stringResource(id = R.string.location_refused_allow))
                    }

                }
            }
        }
    }

    /**
     * Checks if location permissions are granted.
     *
     * @return true if both ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions are granted; false otherwise.
     */
    @OptIn(ExperimentalPagerApi::class, ExperimentalAnimationApi::class)
    fun areLocationPermissionsGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            MainActivity.activityInstance, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    MainActivity.activityInstance, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

}


