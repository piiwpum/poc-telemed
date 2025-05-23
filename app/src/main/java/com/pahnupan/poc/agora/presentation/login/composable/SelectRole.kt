package com.pahnupan.poc.agora.presentation.login.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pahnupan.poc.agora.composable.theme.PurpleGrey80
import com.pahnupan.poc.agora.composable.theme.Typography
import com.pahnupan.poc.agora.presentation.login.LoginUiState

@Composable
fun SelectRole(
    modifier: Modifier,
    role: LoginUiState.Role?,
    onSelect: (LoginUiState.Role) -> Unit = {}
) {
    Row(
        modifier = modifier
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(16.dp),
                color = Color.Black
            )
            .background(
                color = Color.LightGray.copy(alpha = 0.5F),
                shape = RoundedCornerShape(16.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .background(color = if (role == LoginUiState.Role.PATIENT) PurpleGrey80 else Color.White)
                .clickable { onSelect.invoke(LoginUiState.Role.PATIENT) }
                .padding(8.dp),
            text = "Patient",
            textAlign = TextAlign.Center,
            style = Typography.headlineSmall
        )
        Spacer(modifier = Modifier.width(1.dp))
        Text(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .background(color = if (role == LoginUiState.Role.DOCTOR) PurpleGrey80 else Color.White)
                .clickable { onSelect.invoke(LoginUiState.Role.DOCTOR) }
                .padding(8.dp),
            text = "Doctor",
            textAlign = TextAlign.Center,
            style = Typography.headlineSmall
        )
    }
}