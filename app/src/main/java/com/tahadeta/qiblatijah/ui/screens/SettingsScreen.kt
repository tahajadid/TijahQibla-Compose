@file:OptIn(ExperimentalAnimationApi::class)

package com.tahadeta.qiblatijah.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.ColorBlue
import com.tahadeta.qiblatijah.ui.theme.ColorCorrect
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.RightLabelColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular
import com.tahadeta.qiblatijah.utils.compassUtils.getTheRightImage
import com.tahadeta.qiblatijah.utils.locationUtils.LocationUtil.areLocationPermissionsGranted


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit = {},
    onLanguageSwitchClick: () -> Unit = {},
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
                        .background(MaterialTheme.colors.surface),
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
                Box {

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

                    // list of settings
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(padding)
                            .verticalScroll(
                                state = rememberScrollState(),
                            ),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {

                        // Header Description
                        AddSpace(20)

                        Text(
                            text = stringResource(id = R.string.setting_qibla_title),
                            modifier = Modifier.padding(start = 24.dp),
                            color = RightLabelColor,
                            fontFamily = katibehRegular,
                            fontSize = 28.sp,
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .padding(top = 4.dp, start = 24.dp)
                                .background(RightLabelColor)
                        )
                        CorrectQiblaDescription()

                        /*
                        // switch language settings
                        AddSpace(20)

                        Text(
                            text = stringResource(id = R.string.setting_language_title),
                            modifier = Modifier.padding(start = 24.dp),
                            color = RightLabelColor,
                            fontFamily = katibehRegular,
                            fontSize = 28.sp,
                        )

                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(6.dp)
                                .padding(top = 4.dp, start = 24.dp)
                                .background(RightLabelColor)
                        )
                        AddSpace(10)

                        val dataStore = PreferencesDataStore(LocalContext.current)
                        val coroutineScope = rememberCoroutineScope()

                        LanguageSwitcher(
                            modifier = Modifier
                                .height(60.dp)
                                .padding(start = 24.dp, end = 24.dp)
                                .clickable {
                                    onLanguageSwitchClick()

                                    coroutineScope.launch {
                                        //setPerAppLanguage()
                                        dataStore.saveLanguageSelected("ar")
                                    } },

                        )
                         */

                        // set location permission
                        AddSpace(30)
                        LocationSettingsSection(!areLocationPermissionsGranted())
                    }
                }
            }
        }
    }
}

@Composable
fun AddSpace(space: Int){
    Spacer(modifier = Modifier.height(space.dp))
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun LocationSettingsSection(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column(){
            Text(
                text = stringResource(id = R.string.setting_location_title),
                modifier = Modifier.padding(start = 24.dp),
                color = RightLabelColor,
                fontFamily = katibehRegular,
                fontSize = 28.sp,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .padding(top = 4.dp, start = 24.dp)
                    .background(RightLabelColor)
            )

            AddSpace(10)

            androidx.compose.material.Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ColorBlue
                ),
                onClick = {
                    MainActivity.activityInstance.callSettings()
                }
            ) {
                androidx.compose.material3.Text(
                    modifier = Modifier.padding(top = 4.dp),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.setting_location_button),
                    fontFamily = katibehRegular,
                    fontSize = 20.sp,
                    color = ScreenBgColor
                )
            }
        }
    }
}

@Composable
fun CorrectQiblaDescription(){
    Text(
        text = stringResource(id = R.string.settings_qibla_description),
        modifier = Modifier.padding(start = 24.dp, end = 16.dp, top = 16.dp),
        color = ColorCorrect,
        fontFamily = katibehRegular,
        fontSize = 22.sp,
        style = TextStyle(
            lineHeight = 30.sp
        )
    )
}


@Composable
@Preview(showBackground = true)
fun SettingsScreenPreview() {
    QiblaTijahTheme {
        SettingsScreen(
            modifier = Modifier,
        )
    }
}