package com.pahnupan.poc.agora.presentation.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pahnupan.poc.agora.composable.theme.PurpleGrey80
import com.pahnupan.poc.agora.composable.theme.Typography
import com.pahnupan.poc.agora.presentation.login.composable.SelectRole
import kotlinx.coroutines.flow.receiveAsFlow


@Composable
fun LoginRoute(
    viewModel: LoginViewModel,
    navigateToLanding : (role : LoginUiState.Role) -> Unit ={},
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val event = viewModel.event.receiveAsFlow()
        .collectAsStateWithLifecycle(initialValue = LoginEvent.OnInit).value

    LaunchedEffect(event) {
        when (event) {
            LoginEvent.OnInit -> Unit

            LoginEvent.OnLoginFail -> {
                showToast(context, "Login Fail , Please Retry")
            }

            is LoginEvent.OnLoginSuccess -> {
                navigateToLanding.invoke(event.role)
                showToast(context, "Login Success")
            }
        }
    }

    LoginScreen(
        uiState = uiState,
        onUserChange = {
            viewModel.emitIntent(LoginIntent.OnUserChange(value = it))
        },
        onChangeRole = {
            viewModel.emitIntent(LoginIntent.OnChangeRole(role = it))

        },
        onClickLogin = {
            viewModel.emitIntent(LoginIntent.OnClickLogin)
        }

    )
}

private fun showToast(context: Context, mess: String) {
    Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
}

@Composable
private fun LoginScreen(
    uiState: LoginUiState = LoginUiState(),
    onUserChange: (String) -> Unit = {},
    onChangeRole: (LoginUiState.Role) -> Unit = {},
    onClickLogin: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Welcome POC Agora by Piiw",
                style = Typography.titleLarge.copy(
                    fontWeight = FontWeight(500)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                value = uiState.user,
                onValueChange = onUserChange,
                label = { Text("user") },
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(8.dp))

            SelectRole(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                role = uiState.role,
                onSelect = {
                    onChangeRole.invoke(it)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                enabled = uiState.enableButton || uiState.loading,
                onClick = onClickLogin
            ) {
                if (uiState.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.White
                    )
                } else {
                    Text("Login")
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
//    LoginRoute(viewModel = hiltViewModel())

    LoginScreen(
        uiState = LoginUiState(
            user = "verear",
            enableButton = true,
            loading = false
        ),
        onChangeRole = {},
        onClickLogin = {})


}