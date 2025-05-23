package com.pahnupan.poc.agora.domain.usecase

import com.pahnupan.poc.agora.core.SharePref
import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.data.request.EndSessionRequest
import com.pahnupan.poc.agora.domain.reprository.TelemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EndSessionUseCase @Inject constructor(
    private val telemedRepository: TelemedRepository,
) {
    suspend fun invoke(sessionId: Int): Flow<NetworkResult<Unit>> {
        return telemedRepository.endSession(request = EndSessionRequest(sessionId = sessionId))
    }
}