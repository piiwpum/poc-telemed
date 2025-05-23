package com.pahnupan.poc.agora.domain.reprository

import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.data.request.BookingQueueRequest
import com.pahnupan.poc.agora.data.request.EndSessionRequest
import com.pahnupan.poc.agora.data.request.LoginRequest
import com.pahnupan.poc.agora.domain.entries.BookingQueueEntity
import com.pahnupan.poc.agora.domain.entries.QueueEntity
import kotlinx.coroutines.flow.Flow

interface TelemedRepository {
    suspend fun login(request: LoginRequest): Flow<NetworkResult<String>>
    suspend fun getQueues(): Flow<NetworkResult<List<QueueEntity>>>
    suspend fun bookingQueue(request: BookingQueueRequest): Flow<NetworkResult<BookingQueueEntity>>
    suspend fun endSession(request: EndSessionRequest): Flow<NetworkResult<Unit>>
}