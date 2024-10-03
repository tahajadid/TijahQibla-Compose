package com.tahadeta.qiblatijah.utils.onBoardingPage

import androidx.annotation.DrawableRes
import com.tahadeta.qiblatijah.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object First : OnBoardingPage(
        image = R.drawable.onboarding_direction,
        title = "Direction de l'Qaaba",
        description = "Vous devez activer la location pour beneficier de cette fonctionnalité"
    )

    object Second : OnBoardingPage(
        image = R.drawable.onboarding_location,
        title = "Activation de localisation",
        description = "Qibla Tijah vous aidera pour trouver la direction de prière"
    )
}