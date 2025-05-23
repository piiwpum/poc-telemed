package com.pahnupan.poc.agora.presentation.login


sealed interface LoginIntent {
    data object OnInit : LoginIntent
    data object OnClickLogin : LoginIntent
    data class OnUserChange(val value: String) : LoginIntent
    data class OnChangeRole(val role: LoginUiState.Role) : LoginIntent
}