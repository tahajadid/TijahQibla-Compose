package com.tahadeta.qiblatijah.ui.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.RightBrownColor
import com.tahadeta.qiblatijah.ui.theme.RightLabelColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular


@Composable
fun HelpScreen(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit = {},
    layoutDirection : LayoutDirection = LayoutDirection.Ltr
) {
    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        // Your composable content here

        Scaffold(
            backgroundColor = ScreenBgColor,
            topBar = {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(ScreenBgColor),
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(24.dp)
                            .size(32.dp)
                            .offset(0.dp, (20).dp)
                            .clickable {
                                onArrowBackClick()
                            },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        ) { padding ->

            Surface() {
                Box(modifier = Modifier
                    .background(ScreenBgColor)
                    .fillMaxSize()
                    .padding(padding),
                    contentAlignment = Alignment.Center
                ) {

                    // background Image
                    Image(
                        painter = painterResource(
                            id = R.drawable.kaaba_logo
                        ),
                        contentDescription = "compass image",
                        modifier = Modifier
                            .offset(40.dp, (20).dp)
                            .alpha(0.2F)
                            .align(Alignment.BottomEnd)
                    )

                    HelpChangeRotationContainer(Modifier.align(Alignment.Center))

                }
            }
        }
    }
}

@Composable
fun HelpChangeRotationContainer(modifier: Modifier) {
    Column(modifier = modifier.padding(bottom = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // title
        Text(
            text = stringResource(id = R.string.help_content_title),
            modifier = Modifier
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            color = RightLabelColor,
            textAlign = TextAlign.Center,
            fontFamily = katibehRegular,
            fontSize = 28.sp,
        )
        // description
        Text(
            text = stringResource(id = R.string.help_content_description),
            modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 40.dp),
            color = RightBrownColor,
            textAlign = TextAlign.Center,
            fontFamily = katibehRegular,
            fontSize = 22.sp,
        )
        // info rotation Image
        Image(
            painter = painterResource(
                id = R.drawable.rotation_info
            ),
            contentDescription = "compass image",
            modifier = Modifier
                .fillMaxWidth(0.9f)
        )

    }
}
@Composable
@Preview(showBackground = true)
fun HelpScreenScreenPreview() {
    QiblaTijahTheme {
        HelpScreen(
            modifier = Modifier,
        )
    }
}