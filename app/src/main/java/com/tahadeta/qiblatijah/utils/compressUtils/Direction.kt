package com.tahadeta.qiblatijah.utils.compressUtils

import android.content.Context
import com.tahadeta.qiblatijah.R

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
