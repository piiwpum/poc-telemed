package com.pahnupan.poc.agora.domain.entries


data class BookingQueueEntity(
    val queueId: Int,
    val patientId: Int,
    val status: String
)
