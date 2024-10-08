package com.tahadeta.qiblatijah.ui.components.compass

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tahadeta.qiblatijah.utils.Constants.COMPASS_CENTER_TOP_POSITION
import com.tahadeta.qiblatijah.utils.compassUtils.getRotation

@Composable
fun DefaultCompass(
    modifier: Modifier = Modifier,
    degrees: Int = 360,
    rotateCompass: Boolean,
    variantWidget: @Composable (rotationAngle: Float) -> Unit,
) {

    // this keeps last rotation
    val (lastRotation, setLastRotation) = remember { mutableStateOf(0) }

    /**
     * when we want to make the compass static we should set the degree to the init
     * value (360)
     */
    val newRotation = getRotation(if(rotateCompass) degrees else 360, lastRotation)

    setLastRotation(newRotation)

    // negative value to rotate in opposite direction
    val targetRotation = -(newRotation - COMPASS_CENTER_TOP_POSITION)

    val rotationAngle by animateFloatAsState(
        targetValue = targetRotation.toFloat(),
        animationSpec =
            tween(
                durationMillis = 300,
                easing = LinearEasing,
            ),
    )

    variantWidget(rotationAngle)
}
