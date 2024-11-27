package com.tahadeta.qiblatijah.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tahadeta.qiblatijah.R
import com.tahadeta.qiblatijah.ui.theme.ColorCorrect
import com.tahadeta.qiblatijah.ui.theme.ColorWrong
import com.tahadeta.qiblatijah.ui.theme.ScreenBgColor
import com.tahadeta.qiblatijah.ui.theme.ScreenBgOpacityColor
import com.tahadeta.qiblatijah.ui.theme.katibehRegular

@Composable
fun UpdateAppDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertCustomDialog(
        onDismissRequest,
        onConfirmation,
        "",
        stringResource(id = R.string.update_app_title),
        stringResource(id = R.string.update_app_confirm),
        stringResource(id = R.string.update_app_cancel),
        icon = Icons.Default.Info
    )

}


@Composable
fun AlertCustomDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmText: String,
    cancelText: String,
    icon: ImageVector,
) {
    AlertDialog(
        containerColor = Color.White,
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(confirmText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(cancelText)
            }
        }
    )
}