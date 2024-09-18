package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.ColorCorrect
import com.tahadeta.qiblatijah.ui.theme.ColorWrong
import com.tahadeta.qiblatijah.ui.theme.QiblaTijahTheme
import com.tahadeta.qiblatijah.ui.theme.RightLabelColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular

@Suppress("ktlint:standard:function-naming")
@Composable
fun LanguageSwitcher(
    modifier: Modifier = Modifier,
    isFrench: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(ColorCorrect)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 8.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(if(isFrench) RightLabelColor else ColorCorrect ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.lang_fr),
                color = ScreenBgColor,
                fontFamily = katibehRegular,
                fontSize = 30.sp,
                overflow = TextOverflow.Clip,
                softWrap = false,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 4.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(if(!isFrench) RightLabelColor else ColorCorrect ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.lang_ar),
                color = ScreenBgColor,
                fontFamily = katibehRegular,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun LanguageSwitcherPreview() {
    QiblaTijahTheme {
        LanguageSwitcher()
    }
}

