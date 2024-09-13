package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.utils.compassUtils.getDirectionsLabel

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

            // Kaaba image for the right direction
            Image(
                modifier = Modifier.size(80.dp),
                painter = painterResource(id = R.drawable.kaaba_logo),
                contentDescription = "kaaba logo",
            )

            // background image
            Image(
                painter = painterResource(id = imageSrc),
                contentDescription = "compass image",
                modifier = Modifier
                    .offset(0.dp, (-10).dp)
                    .rotate(rotationAngle),
            )

            // add vertical space
            Spacer(modifier = Modifier.height(40.dp))

            // add degree and direction values
            DegreeAndDirection(degrees)

            // show message for the user
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
fun DegreeAndDirection(degrees: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${degrees}º",
            color = MaterialTheme.colors.onBackground,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h2,
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "(${getDirectionsLabel(LocalContext.current, degrees)})",
            color = MaterialTheme.colors.onBackground,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h2,
        )
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
