package com.pahnupan.poc.agora.data.helper

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val error: AppError) : NetworkResult<Nothing>()
}

data class AppError(
    val code: Int,
    val message: String,
)
