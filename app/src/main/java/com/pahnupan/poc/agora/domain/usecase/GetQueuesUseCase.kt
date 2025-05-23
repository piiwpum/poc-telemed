package com.pahnupan.poc.agora.domain.usecase

import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.domain.entries.QueueEntity
import com.pahnupan.poc.agora.domain.reprository.TelemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQueuesUseCase @Inject constructor(
    private val telemedRepository: TelemedRepository
) {
    suspend fun invoke(): Flow<NetworkResult<List<QueueEntity>>> {
        return telemedRepository.getQueues()
    }
}