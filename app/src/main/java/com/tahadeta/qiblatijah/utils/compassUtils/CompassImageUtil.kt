package com.tahadeta.qiblatijah.utils.compassUtils

import com.tahadeta.qiblatijah.R

fun getTheRightImage(
    degrees: Int,
): Int =
    when (degrees) {
        in 92..94 -> R.drawable.correct_compass_new
        else -> R.drawable.default_compass_new
    }
