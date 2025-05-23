package com.pahnupan.poc.agora.data.response

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("token")
    val token: String
)



