package com.pahnupan.poc.agora.data.service

import com.pahnupan.poc.agora.data.request.BookingQueueRequest
import com.pahnupan.poc.agora.data.request.EndSessionRequest
import com.pahnupan.poc.agora.data.request.LoginRequest
import com.pahnupan.poc.agora.data.response.BookingQueueResponse
import com.pahnupan.poc.agora.data.response.EndSessionResponse
import com.pahnupan.poc.agora.data.response.LoginResponse
import com.pahnupan.poc.agora.data.response.QueueResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("/api/queue/waiting")
    suspend fun getQueues(): Response<List<QueueResponse>>

    @POST("/api/queue/request")
    suspend fun bookingQueue(@Body request: BookingQueueRequest): Response<BookingQueueResponse>

    @POST("/api/session/end")
    suspend fun endSession(@Body request: EndSessionRequest): Response<EndSessionResponse>

}