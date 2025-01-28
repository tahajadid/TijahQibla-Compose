package com.tahadeta.qiblatijah.utils.compassUtils

import android.content.Context
import android.util.Log
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.utils.Constants.KAABA_LAT
import com.tahadeta.qiblatijah.utils.Constants.KAABA_LON

/**
 * function that returns the direction Label depending on degree
 */
fun getDirectionsLabel(
    context: Context,
    degrees: Int,
): String =
    when (degrees) {
        in 338..360, in 0..22 -> context.getString(R.string.north)
        in 23..67 -> context.getString(R.string.northeast)
        in 68..112 -> context.getString(R.string.east)
        in 113..157 -> context.getString(R.string.southeast)
        in 158..202 -> context.getString(R.string.south)
        in 203..247 -> context.getString(R.string.southwest)
        in 248..292 -> context.getString(R.string.west)
        in 293..337 -> context.getString(R.string.northwest)
        else -> ""
    }


/**
 * function that calculate bearing for the right angle
 */
fun calculateBearing(
    lat1: Double,
    lon1: Double): Double {
    val phi1 = Math.toRadians(lat1)
    val phi2 = Math.toRadians(KAABA_LAT)
    val deltaLambda = Math.toRadians(KAABA_LON - lon1)

    val y = Math.sin(deltaLambda) * Math.cos(phi2)
    val x = Math.cos(phi1) * Math.sin(phi2) -
            Math.sin(phi1) * Math.cos(phi2) * Math.cos(deltaLambda)

    val theta = Math.atan2(y, x)
    Log.d("TestTheta","theta : "+theta.toString())
    return (Math.toDegrees(theta) + 360) % 360 // Normalize to 0-360 degrees
}