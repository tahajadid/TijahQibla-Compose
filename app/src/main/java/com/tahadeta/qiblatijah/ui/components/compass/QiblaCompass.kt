package com.tahadeta.qiblatijah.ui.components.compass

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.RightLabelColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular
import com.tahadeta.qiblatijah.utils.compassUtils.getDirectionsLabel
import com.tahadeta.qiblatijah.utils.compassUtils.getTheRightImage

@Suppress("ktlint:standard:function-naming")
@Composable
fun QiblaCompass(
    modifier: Modifier = Modifier,
    degrees: Int = 0,
    pointerInitDegree : Int = 20,
    imageSrc: Int?,
    rotateCompass: Boolean,
    isLocationCollected: Boolean,
) {
    DefaultCompass(
        modifier = modifier,
        degrees = degrees,
        rotateCompass = rotateCompass
    ) { rotationAngle ->

        // in case of location is collected we increase the value of alpha to 1F
        Column(
            modifier = modifier.alpha(
                if(!isLocationCollected) 0.5F else 1F
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Kaaba image for the right direction
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.kaaba_logo),
                contentDescription = "kaaba logo",
                )

            Box(){
                // background image
                Image(
                    painter = painterResource(
                        id = imageSrc ?: getTheRightImage(degrees,pointerInitDegree)),
                    contentDescription = "compass image",
                    modifier = Modifier
                        .offset(0.dp, (-16).dp)
                        .rotate(pointerInitDegree.toFloat())
                        // adding this additional degree to be aligned with kaaba image on the top
                        .rotate(90-pointerInitDegree.toFloat())
                        .rotate(rotationAngle),
                )

                // pointer image
                Image(
                    painter = painterResource(id = R.drawable.only_indicator),
                    contentDescription = "compass image",
                    modifier = Modifier
                        .offset(0.dp, (-16).dp)
                        // the first Rotation depending on the Lat and Lon of the user
                        .rotate(pointerInitDegree.toFloat())
                        .rotate(pointerInitDegree.toFloat())
                        // adding this additional degree to be aligned with kaaba image on the top
                        .rotate(90-pointerInitDegree.toFloat())
                        // the muttable angle rotation
                        .rotate(rotationAngle),
                )
            }


            if(rotateCompass){
                // add vertical space
                Spacer(modifier = Modifier.height(40.dp))

                // add degree and direction values
                DegreeAndDirection(degrees, isLocationCollected)

                // show message for the user
                Text(
                    text = if(!isLocationCollected) "" else
                        stringResource(id = R.string.correct_direction) + " $pointerInitDegree°",
                    color = RightLabelColor,
                    fontFamily = katibehRegular,
                    fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun DegreeAndDirection(degrees: Int, isLocationCollected: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = if(!isLocationCollected) "" else "${degrees}º",
            color = MaterialTheme.colors.onBackground,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.h2,
        )

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = if(!isLocationCollected) ""
            else "(${getDirectionsLabel(LocalContext.current, degrees)})",
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
            imageSrc = R.drawable.correct_compass_bg,
            rotateCompass = false,
            isLocationCollected = true
        )
    }
}
