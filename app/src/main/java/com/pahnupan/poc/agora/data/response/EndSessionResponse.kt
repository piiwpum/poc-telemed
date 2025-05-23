package com.pahnupan.poc.agora.data.response

import com.google.gson.annotations.SerializedName

data class EndSessionResponse(
    @SerializedName("session_id")
    val sessionId: Int?
)


