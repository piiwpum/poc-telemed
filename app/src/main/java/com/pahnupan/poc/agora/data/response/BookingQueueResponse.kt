package com.pahnupan.poc.agora.data.response

import com.google.gson.annotations.SerializedName

data class BookingQueueResponse(
    @SerializedName("patient_id")
    val patientId: Int,
    @SerializedName("queue_id")
    val queueId: Int,
    @SerializedName("status")
    val status: String
)



