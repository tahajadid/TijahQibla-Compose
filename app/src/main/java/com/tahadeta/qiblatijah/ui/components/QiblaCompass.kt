package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme


@Composable
fun MinimalCompass(
    modifier: Modifier = Modifier,
    canvasSize: Dp = 300.dp,
    color: Color = MaterialTheme.colors.onBackground,
    degrees: Int = 360
) {

    DefaultCompass(
        modifier = modifier,
        degrees = degrees
    ) { rotationAngle ->
        Box(modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val componentSize = size / 1.25f
                val componentSize2 = componentSize / 1.230f

                compassBorder(
                    componentSize = componentSize,
                    color = color
                )

                compassNeedle(
                    componentSize = componentSize2, startAngle = rotationAngle,
                    color = color
                )

            },
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "${degrees}º",
                color = MaterialTheme.colors.onBackground,
                fontSize = (canvasSize.value * .2f).toInt().sp,
                style = MaterialTheme.typography.h1
            )
        }
    }
}

fun DrawScope.compassNeedle(
    componentSize: Size,
    startAngle: Float,
    color: Color
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = startAngle,
        sweepAngle = size.width * 0.0001f,
        useCenter = false,
        style = Stroke(
            width = size.width * 0.06f,
            cap = StrokeCap.Round
        ),

        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.compassBorder(
    componentSize: Size,
    color: Color
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 0f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = size.width * 0.04f,
            cap = StrokeCap.Round
        ),

        topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
@Preview(showBackground = true)
fun CompassAnimationPreview() {
    QiblaTijahTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            MinimalCompass(degrees = 180)
        }
    }
}
