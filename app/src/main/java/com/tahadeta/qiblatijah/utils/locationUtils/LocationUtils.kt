package com.tahadeta.qiblatijah.utils.locationUtils

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.viewModel.HomeViewModel

object LocationUtils {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @Composable
    fun requestLocation(
        homeViewModel: HomeViewModel
    ){

        // State variables to manage location information and permission result text
        var locationText by remember { mutableStateOf("No location obtained :(") }
        var showPermissionResultText by remember { mutableStateOf(false) }
        var permissionResultText by remember { mutableStateOf("Permission Granted...") }


        // Request location permission using a Compose function
        RequestLocationPermission(
            viewModel = homeViewModel,
            onPermissionGranted = {

                Log.d("TestTT","LocationRequest  -- onPermissionGranted")

                homeViewModel.updateLocationView(true)

                // Callback when permission is granted
                showPermissionResultText = true
                // Attempt to get the last known user location
                getLastUserLocation(
                    onGetLastLocationSuccess = {
                        Log.d("TestTT","LocationRequest  onGetLastLocationSuccess")

                        locationText =
                            "Location using LAST-LOCATION: LATITUDE: ${it.first}, LONGITUDE: ${it.second}"
                    },
                    onGetLastLocationFailed = { exception ->
                        Log.d("TestTT","LocationRequest  onGetLastLocationFailed")

                        showPermissionResultText = true
                        locationText =
                            exception.localizedMessage ?: "Error Getting Last Location"
                    },
                    onGetLastLocationIsNull = {
                        Log.d("TestTT","LocationRequest  onGetLastLocationIsNull")

                        // Attempt to get the current user location
                        getCurrentLocation(
                            onGetCurrentLocationSuccess = {
                                locationText =
                                    "Location using CURRENT-LOCATION: LATITUDE: ${it.first}, LONGITUDE: ${it.second}"
                            },
                            onGetCurrentLocationFailed = {
                                showPermissionResultText = true
                                locationText =
                                    it.localizedMessage
                                        ?: "Error Getting Current Location"
                            }
                        )
                    }
                )
            },
            onPermissionDenied = {
                Log.d("TestTT","LocationRequest  -- onPermissionDenied")

                homeViewModel.updateLocationView(false)
                // Callback when permission is denied
                showPermissionResultText = true
                permissionResultText = "Permission Denied :("
            },
            onPermissionsRevoked = {
                Log.d("TestTT","LocationRequest  -- onPermissionsRevoked")
                Log.d("TestTT","areLocationPermissionsGranted  '''' - areLocationPermissionsGranted"+areLocationPermissionsGranted())
                homeViewModel.updateLocationView(true)

                // Callback when permission is revoked
                showPermissionResultText = true
                permissionResultText = "Permission Revoked :("
            }
        )

    }

    /**
     * Composable function to request location permissions and handle different scenarios.
     *
     * @param onPermissionGranted Callback to be executed when all requested permissions are granted.
     * @param onPermissionDenied Callback to be executed when any requested permission is denied.
     * @param onPermissionsRevoked Callback to be executed when previously granted permissions are revoked.
     */
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun RequestLocationPermission(
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit,
        onPermissionsRevoked: () -> Unit,
        viewModel: HomeViewModel
    ) {
        // Initialize the state for managing multiple location permissions.
        val permissionState = rememberMultiplePermissionsState(
            listOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
        LaunchedEffect(permissionState) {
            // Launch permission request if not granted yet
            permissionState.launchMultiplePermissionRequest()

            // Monitor permission state changes after user response
            permissionState.permissions.forEach { permission ->
                when {
                    permission.status.isGranted -> {
                        Log.d("TestTTt", "Permission Granted: ${permission.permission}")
                        onPermissionGranted()
                    }
                    permission.status.shouldShowRationale -> {
                        Log.d("TestTTt", "Permission Denied with rationale: ${permission.permission}")
                        onPermissionDenied()
                    }
                    !permission.status.isGranted && !permission.status.shouldShowRationale -> {
                        Log.d("TestTTt", "Permission Denied permanently: ${permission.permission}")
                        onPermissionsRevoked()
                    }
                }
            }
    }
    }


    /**
     * Retrieves the last known user location asynchronously.
     *
     * @param onGetLastLocationSuccess Callback function invoked when the location is successfully retrieved.
     *        It provides a Pair representing latitude and longitude.
     * @param onGetLastLocationFailed Callback function invoked when an error occurs while retrieving the location.
     *        It provides the Exception that occurred.
     */
    @SuppressLint("MissingPermission")
    private fun getLastUserLocation(
        onGetLastLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetLastLocationFailed: (Exception) -> Unit,
        onGetLastLocationIsNull: () -> Unit
    ) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.activityInstance)
        // Check if location permissions are granted
        if (areLocationPermissionsGranted()) {
            // Retrieve the last known location
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    location?.let {
                        // If location is not null, invoke the success callback with latitude and longitude
                        onGetLastLocationSuccess(Pair(it.latitude, it.longitude))
                    }?.run {
                        onGetLastLocationIsNull()
                    }
                }
                .addOnFailureListener { exception ->
                    // If an error occurs, invoke the failure callback with the exception
                    onGetLastLocationFailed(exception)
                }
        }
    }


    /**
     * Retrieves the current user location asynchronously.
     *
     * @param onGetCurrentLocationSuccess Callback function invoked when the current location is successfully retrieved.
     *        It provides a Pair representing latitude and longitude.
     * @param onGetCurrentLocationFailed Callback function invoked when an error occurs while retrieving the current location.
     *        It provides the Exception that occurred.
     * @param priority Indicates the desired accuracy of the location retrieval. Default is high accuracy.
     *        If set to false, it uses balanced power accuracy.
     */
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation(
        onGetCurrentLocationSuccess: (Pair<Double, Double>) -> Unit,
        onGetCurrentLocationFailed: (Exception) -> Unit,
        priority: Boolean = true
    ) {
        // Determine the accuracy priority based on the 'priority' parameter
        val accuracy = if (priority) Priority.PRIORITY_HIGH_ACCURACY
        else Priority.PRIORITY_BALANCED_POWER_ACCURACY

        // Check if location permissions are granted
        if (areLocationPermissionsGranted()) {
            // Retrieve the current location asynchronously
            fusedLocationProviderClient.getCurrentLocation(
                accuracy, CancellationTokenSource().token,
            ).addOnSuccessListener { location ->
                location?.let {
                    // If location is not null, invoke the success callback with latitude and longitude
                    onGetCurrentLocationSuccess(Pair(it.latitude, it.longitude))
                }?.run {
                    //Location null do something
                }
            }.addOnFailureListener { exception ->
                // If an error occurs, invoke the failure callback with the exception
                onGetCurrentLocationFailed(exception)
            }
        }
    }


    /**
     * Checks if location permissions are granted.
     *
     * @return true if both ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION permissions are granted; false otherwise.
     */
    fun areLocationPermissionsGranted(): Boolean {
        return (ActivityCompat.checkSelfPermission(
            MainActivity.activityInstance, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    MainActivity.activityInstance, android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }
}