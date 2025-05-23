package com.pahnupan.poc.agora.data.request

import com.google.gson.annotations.SerializedName

data class BookingQueueRequest(
    @SerializedName("patient_id")
    val patientId: Int,
)
