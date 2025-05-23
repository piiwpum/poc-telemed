package com.pahnupan.poc.agora.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pahnupan.poc.agora.composable.theme.Typography


@Composable
fun HeaderBar(
    title: String,
    onClickLogout: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 44.dp)
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.size(44.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            text = title,
            style = Typography.headlineSmall.copy(fontWeight = FontWeight(500))
        )
        IconButton(onClick = onClickLogout) {
            Icon(imageVector = Icons.Rounded.Logout, contentDescription = "")
        }
    }
}

@Preview
@Composable
private fun PreviewHeader() {
    HeaderBar("screen name")
}