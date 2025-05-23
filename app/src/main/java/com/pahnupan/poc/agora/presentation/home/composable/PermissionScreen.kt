package com.pahnupan.poc.agora.presentation.home.composable

import android.content.res.Resources.Theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
internal fun PermissionScreen(
    modifier: Modifier = Modifier,
    onClickRequirePermission: () -> Unit = {}
) {
    Column(
        modifier = modifier.background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = "Allow your Permission",
            style = TextStyle(
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal
            )
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = "This app needs access to your Camera and Micophone \n\nPlease allow your permission",
            style = TextStyle(
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Center
            )
        )

        Button(
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            onClick = onClickRequirePermission
        ) {
            Text("Allow Permission")
        }
    }
}

@Preview(apiLevel = 34)
@Composable
private fun PermissionScreenPreview() {
    PermissionScreen(
        modifier = Modifier.fillMaxSize()
    )
}