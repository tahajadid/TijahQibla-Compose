package com.tahadeta.qiblatijah.utils.compassUtils

import com.tahadeta.qiblatijah.R

fun getTheRightImage(
    actualDegrees: Int,
    qiblaDegree: Int
): Int =
    when (actualDegrees) {
        in (qiblaDegree-2)..(qiblaDegree+2) -> R.drawable.correct_compass_bg
        else -> R.drawable.default_compass_bg
    }
