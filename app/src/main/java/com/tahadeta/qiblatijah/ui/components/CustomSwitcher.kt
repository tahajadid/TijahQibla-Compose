package com.tahadeta.qiblatijah.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme

@Composable
fun CustomSwitchButton(
    switchPadding: Dp,
    buttonWidth: Dp,
    buttonHeight: Dp,
    value: Boolean
) {

    val switchSize by remember {
        mutableStateOf(buttonHeight-switchPadding*2)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    var switchClicked by remember {
        mutableStateOf(value)
    }

    var padding by remember {
        mutableStateOf(0.dp)
    }

    padding = if (switchClicked) buttonWidth-switchSize-switchPadding*2 else 0.dp

    val animateSize by animateDpAsState(
        targetValue = if (switchClicked) padding else 0.dp,
        tween(
            durationMillis = 700,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(CircleShape)
            .background(if (switchClicked) Color.DarkGray else Color.LightGray)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {

                switchClicked = !switchClicked

            }
    ){
        Row(modifier = Modifier.fillMaxSize().padding(switchPadding)) {

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(animateSize)
                    .background(Color.Transparent)
            )

            Box(modifier = Modifier
                .size(switchSize)
                .clip(CircleShape)
                .background(Color.White))

        }
    }
}

@Composable
@Preview(showBackground = true)
fun CustomSwitcherPreview() {
    QiblaTijahTheme {
        CustomSwitchButton(
            switchPadding =  40.dp,
            buttonWidth =  140.dp,
            buttonHeight = 40.dp,
            value = false
        )
    }
}
