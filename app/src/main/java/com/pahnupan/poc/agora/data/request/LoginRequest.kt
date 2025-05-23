package com.pahnupan.poc.agora.data.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("role")
    val role: String
)
