package com.pahnupan.poc.agora.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pahnupan.poc.agora.composable.theme.Typography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogConfirmLeaveChanel(
    onClickConfirm: () -> Unit = {},
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(onDismissRequest = { onDismiss.invoke() }) {
        Column(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Confirm to end session", style = Typography.titleLarge)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Confirm to end the telemed session", style = Typography.bodyMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onClickConfirm.invoke()
                }) {
                Text("confirm")
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(width = 1.dp, color = Color.DarkGray),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray
                ),
                onClick = {
                    onDismiss.invoke()
                }) {
                Text("cancel")
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialogConfirmLeaveChanel() {
    var isShowDialog by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { isShowDialog = !isShowDialog }) {
            Text("show dialog")
        }

        if (isShowDialog) {
            DialogConfirmLeaveChanel(
                onClickConfirm = {
                    isShowDialog = false
                },
                onDismiss = { isShowDialog = false }
            )
        }
    }
}