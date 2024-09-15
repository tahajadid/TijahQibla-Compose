package com.tahadeta.qiblatijah.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor


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
                        .offset(0.dp,(20).dp)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(R.string.widget),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onSurface.copy(0.7f)
                )



                Spacer(Modifier.padding(5.dp))

                Spacer(Modifier.padding(top = 50.dp, end = 4.dp))


            }
        }
    }
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