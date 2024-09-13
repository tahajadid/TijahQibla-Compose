package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme

@Suppress("ktlint:standard:function-naming")
@Composable
fun QiblaCompass(
    modifier: Modifier = Modifier,
    degrees: Int = 193,
    imageSrc: Int = 0,
) {
    DefaultCompass(
        modifier = modifier,
        degrees = degrees,
    ) { rotationAngle ->

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.kaaba_logo),
                contentDescription = "kaaba_logo",
            )

            Image(
                painter = painterResource(id = imageSrc),
                contentDescription = "Compass Needle",
                modifier = Modifier
                    .offset(0.dp, (-10).dp)
                    .rotate(rotationAngle),
            )

            // add a spacer
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "${degrees}º",
                color = MaterialTheme.colors.onBackground,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.h2,
            )

            Text(
                text = "La direction exacte : 93º",
                color = MaterialTheme.colors.onBackground,
                fontSize = 20.sp,
                style = MaterialTheme.typography.h4,
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun CompassAnimationPreview() {
    QiblaTijahTheme {
        QiblaCompass(
            imageSrc = R.drawable.correct_compass
        )
    }
}
