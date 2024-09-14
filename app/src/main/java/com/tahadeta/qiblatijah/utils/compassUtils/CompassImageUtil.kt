package com.tahadeta.qiblatijah.utils.compassUtils

import com.tahadeta.qiblatijah.R

fun getTheRightImage(
    degrees: Int,
): Int =
    when (degrees) {
        in 91..95 -> R.drawable.correct_compass
        else -> R.drawable.default_compass
    }
