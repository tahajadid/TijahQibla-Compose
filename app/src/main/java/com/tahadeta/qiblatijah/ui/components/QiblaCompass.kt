package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun QiblaCompass(
    modifier: Modifier = Modifier,
    degrees: Int = 193,
    imageRrc: Int = 0,
) {
    DefaultCompass(
        modifier = modifier,
        degrees = degrees,
    ) { rotationAngle ->

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = imageRrc),
                contentDescription = "Compass Needle",
                modifier = Modifier.rotate(rotationAngle),
            )

            Text(
                text = "${degrees}º",
                color = MaterialTheme.colors.onBackground,
                fontSize = 30.sp,
                style = MaterialTheme.typography.h1,
            )
        }

    }
}

fun DrawScope.compassNeedle(
    componentSize: Size,
    startAngle: Float,
    color: Color,
) {
    drawArc(
        size = componentSize,
        color = color,
        startAngle = startAngle,
        sweepAngle = size.width * 0.0001f,
        useCenter = false,
        style =
            Stroke(
                width = size.width * 0.06f,
                cap = StrokeCap.Round,
            ),
        topLeft =
            Offset(
                x = (size.width - componentSize.width) / 2f,
                y = (size.height - componentSize.height) / 2f,
            ),
    )
}

@Composable
@Preview(showBackground = true)
fun CompassAnimationPreview() {
    QiblaTijahTheme {
        QiblaCompass()
    }
}
