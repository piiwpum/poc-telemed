package com.pahnupan.poc.agora.data.request

import com.google.gson.annotations.SerializedName

data class EndSessionRequest(
    @SerializedName("session_id")
    val sessionId: Int?
)


