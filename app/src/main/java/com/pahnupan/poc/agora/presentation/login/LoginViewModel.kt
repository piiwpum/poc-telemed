package com.pahnupan.poc.agora.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahnupan.poc.agora.core.SharePref
import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharePref: SharePref
) : ViewModel() {

    val uiState = MutableStateFlow(LoginUiState())
    val event = Channel<LoginEvent>()
    private val intent = MutableSharedFlow<LoginIntent>()

    init {
        handleIntent()
    }

    fun emitIntent(action: LoginIntent) {
        viewModelScope.launch {
            intent.emit(action)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collectLatest {
                when (it) {
                    LoginIntent.OnInit -> {
                        Unit
                    }

                    LoginIntent.OnClickLogin -> {
                        login()
                    }

                    is LoginIntent.OnChangeRole -> {
                        onChangeRole(role = it.role)
                    }

                    is LoginIntent.OnUserChange -> {
                        onUpdateUser(it.value)
                    }
                }
            }
        }
    }

    private fun onUpdateUser(user: String) {
        uiState.update {
            it.copy(
                user = user,
                enableButton = user.isNotBlank()
            )
        }
    }

    private fun onChangeRole(role: LoginUiState.Role) {
        uiState.update { it.copy(role = role) }
    }

    private fun login() {
        viewModelScope.launch {
            val uid = uiState.value.user
            val role = uiState.value.role
            loginUseCase.invoke(
                userId = uid,
                role = role ?: LoginUiState.Role.PATIENT
            ).onStart {
                uiState.update { it.copy(loading = true) }
                delay(500)
            }.collect { response ->
                uiState.update { it.copy(loading = false) }
                if (response is NetworkResult.Success) {
                    sharePref.token = response.data
                    sharePref.role = role?.name ?: LoginUiState.Role.PATIENT.name
                    sharePref.uid = uid.toIntOrNull() ?: 0
                    event.send(LoginEvent.OnLoginSuccess(role = role ?: LoginUiState.Role.PATIENT))
                } else {
                    event.send(LoginEvent.OnLoginFail)
                }
            }
        }
    }
}