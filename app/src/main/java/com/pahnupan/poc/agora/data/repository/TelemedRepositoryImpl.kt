package com.pahnupan.poc.agora.data.repository

import com.pahnupan.poc.agora.data.helper.AppError
import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.data.request.BookingQueueRequest
import com.pahnupan.poc.agora.data.request.EndSessionRequest
import com.pahnupan.poc.agora.data.request.LoginRequest
import com.pahnupan.poc.agora.data.service.ApiService
import com.pahnupan.poc.agora.domain.entries.BookingQueueEntity
import com.pahnupan.poc.agora.domain.entries.QueueEntity
import com.pahnupan.poc.agora.domain.reprository.TelemedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TelemedRepositoryImpl @Inject constructor(
    private val api: ApiService
) : TelemedRepository {
    override suspend fun login(request: LoginRequest): Flow<NetworkResult<String>> {
        return flow {
            try {
                val result = api.login(request)
                if (result.isSuccessful && result.body() != null) {
                    val data = result.body()!!
                    emit(
                        NetworkResult.Success(data.token)
                    )
                } else {
                    emit(
                        NetworkResult.Error(
                            AppError(
                                code = result.code(),
                                message = result.message()
                            )
                        )
                    )
                }
            } catch (ex: Exception) {
                emit(
                    NetworkResult.Error(
                        AppError(
                            code = 0,
                            message = "Exception"
                        )
                    )
                )
            }
        }
    }

    override suspend fun getQueues(): Flow<NetworkResult<List<QueueEntity>>> {
        return flow {
            try {
                val result = api.getQueues()
                if (result.isSuccessful && result.body() != null) {
                    val data = result.body()!!
                    emit(
                        NetworkResult.Success(data.map {
                            QueueEntity(
                                userId = it.queueId.toString(),
                                queueId = it.queueId.toString(),
                                status = it.status.toString()
                            )
                        })
                    )
                } else {
                    emit(
                        NetworkResult.Error(
                            AppError(
                                code = result.code(),
                                message = result.message()
                            )
                        )
                    )
                }
            } catch (ex: Exception) {
                emit(
                    NetworkResult.Error(
                        AppError(
                            code = 0,
                            message = "Exception"
                        )
                    )
                )
            }
        }
    }

    override suspend fun bookingQueue(request: BookingQueueRequest): Flow<NetworkResult<BookingQueueEntity>> {
        return flow {
            try {
                val result = api.bookingQueue(request)
                if (result.isSuccessful && result.body() != null) {
                    val data = result.body()!!
                    emit(
                        NetworkResult.Success(
                            BookingQueueEntity(
                                queueId = data.queueId,
                                patientId = data.patientId,
                                status = data.status
                            )
                        )
                    )
                } else {
                    emit(
                        NetworkResult.Error(
                            AppError(
                                code = result.code(),
                                message = result.message()
                            )
                        )
                    )
                }
            } catch (ex: Exception) {
                emit(
                    NetworkResult.Error(
                        AppError(
                            code = 0,
                            message = "Exception"
                        )
                    )
                )
            }
        }
    }


    override suspend fun endSession(request: EndSessionRequest): Flow<NetworkResult<Unit>> {
        return flow {
            try {
                val result = api.endSession(request)
                if (result.isSuccessful && result.body() != null) {
                    val data = result.body()!!
                    emit(
                        NetworkResult.Success(Unit)
                    )
                } else {
                    emit(
                        NetworkResult.Error(
                            AppError(
                                code = result.code(),
                                message = result.message()
                            )
                        )
                    )
                }
            } catch (ex: Exception) {
                emit(
                    NetworkResult.Error(
                        AppError(
                            code = 0,
                            message = "Exception"
                        )
                    )
                )
            }
        }

    }
}