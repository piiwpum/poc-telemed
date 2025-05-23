package com.pahnupan.poc.agora.domain.usecase

import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.data.request.LoginRequest
import com.pahnupan.poc.agora.domain.reprository.TelemedRepository
import com.pahnupan.poc.agora.presentation.login.LoginUiState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.random.Random


class LoginUseCase @Inject constructor(
    private val telemedRepository: TelemedRepository
) {
    suspend fun invoke(userId: String, role: LoginUiState.Role): Flow<NetworkResult<String>> {
        return telemedRepository.login(
            request = LoginRequest(
                userId = userId.toIntOrNull() ?: Random.nextInt(),
                role = if (role == LoginUiState.Role.PATIENT) "patient" else "doctor"
            )
        )
    }
}