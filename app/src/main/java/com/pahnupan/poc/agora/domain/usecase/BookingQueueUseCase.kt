package com.pahnupan.poc.agora.domain.usecase

import com.pahnupan.poc.agora.core.SharePref
import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.data.request.BookingQueueRequest
import com.pahnupan.poc.agora.domain.entries.BookingQueueEntity
import com.pahnupan.poc.agora.domain.reprository.TelemedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookingQueueUseCase @Inject constructor(
    private val telemedRepository: TelemedRepository,
    private val sharePref: SharePref
) {
    suspend fun invoke(): Flow<NetworkResult<BookingQueueEntity>> {
        return telemedRepository.bookingQueue(request = BookingQueueRequest(sharePref.uid ?: 0))
    }
}