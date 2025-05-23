package com.pahnupan.poc.agora.presentation.login

data class LoginUiState(
    val user: String = "",
    val role: Role? = null,
    val enableButton: Boolean = false,
    val loading: Boolean = false
) {
    enum class Role {
        PATIENT, DOCTOR
    }
}

sealed interface LoginEvent {
    data object OnInit : LoginEvent
    data object OnLoginFail : LoginEvent
    data class OnLoginSuccess(val role: LoginUiState.Role) : LoginEvent
}