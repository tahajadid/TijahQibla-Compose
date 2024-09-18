package com.tahadeta.qiblatijah.ui.screens

import android.view.View
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.components.LanguageSwitcher
import com.tahadeta.qiblatijah.ui.theme.ColorCorrect
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.RightLabelColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit = {},
    onWidgetChoose: (String) -> Unit = {}
) {

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
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.setting_qibla_title),
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

                CorrectQiblaDescription()

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(6.dp)
                        .padding(top = 4.dp, start = 24.dp)
                        .background(RightLabelColor)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(id = R.string.setting_language_title),
                    modifier = Modifier.padding(start = 24.dp),
                    color = RightLabelColor,
                    fontFamily = katibehRegular,
                    fontSize = 28.sp,
                )

                Spacer(modifier = Modifier.height(6.dp))

                LanguageSwitcher(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(start = 24.dp, end = 24.dp),

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
fun SwitchSettingLanguage(){

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