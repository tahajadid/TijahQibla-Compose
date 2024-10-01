package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.ColorBlue
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgOpacityColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtils

@Composable
fun LocationRequestSectionHolder(
    modifier: Modifier = Modifier,
    ) {

    Box(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth()
            .padding(bottom = 40.dp)
    ) {
        Box(modifier = Modifier.background(ScreenBgOpacityColor))
    }

    Box(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth()
            .padding(bottom = 40.dp),
    ) {
        LocationRequestSection(
            modifier = modifier,
            true,
            )
    }
}

@Suppress("ktlint:standard:function-naming")
@Composable
fun LocationRequestSection(
    modifier: Modifier = Modifier,
    isFrench: Boolean = true,
) {
    var showComposable by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // background image
        Image(
            painter = painterResource(id = R.drawable.location),
            contentDescription = "compass image",
            modifier = Modifier
                .height(100.dp)
                .padding(top = 10.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            stringResource(id = R.string.active_location_description),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontFamily = katibehRegular,
            color = ColorBlue,
            modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp),
        )


        Button(
            modifier = Modifier.padding(bottom = 10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorBlue
            ),
            onClick = {
                showComposable = !showComposable
            }
        ) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.active_location),
                fontFamily = katibehRegular,
                fontSize = 20.sp,
                color = ScreenBgColor
            )
        }

    }
}

@Composable
@Preview(showBackground = true)
fun LocationRequestSectionPreview() {
    QiblaTijahTheme {
        LocationRequestSection()
    }
}
