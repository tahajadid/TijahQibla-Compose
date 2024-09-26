package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tahadeta.qiblatijah.MainActivity
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.ColorCorrect
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor

@Composable
fun CustomDialogAlert(
    modifier: Modifier = Modifier,
    text: String) {

    var showDialog by remember {
        mutableStateOf(true)
    }

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
        ) {
            Card(
                modifier = modifier,
                backgroundColor = ScreenBgColor,
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.padding(bottom = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                        text = text,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = ColorCorrect
                        ),
                        onClick = {
                        showDialog = false }) {
                            Text(
                                text = stringResource(R.string.close_app),
                                color = ScreenBgColor
                            )
                    }
                }

            }
        }
    } else {
        // close the application by finishing the Activity
        MainActivity.activityInstance.finish()
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    QiblaTijahTheme {
        CustomDialogAlert(
            modifier = Modifier,
            "onDism fefferfissRequest dwedq wed rrfer" +
                    "quest dw onDism issRequest onDismif ssRequest ",
        )
    }
}