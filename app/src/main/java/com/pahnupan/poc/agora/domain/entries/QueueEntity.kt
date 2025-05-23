package com.pahnupan.poc.agora.domain.entries

data class QueueEntity(
    val userId: String,
    val queueId: String,
    val status: String
)