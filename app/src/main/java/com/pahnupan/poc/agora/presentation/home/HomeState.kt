package com.pahnupan.poc.agora.presentation.home

import com.pahnupan.poc.agora.domain.entries.BookingQueueEntity


data class HomeUiState(
    val loading : Boolean = false,
    val string: String
)

sealed interface HomeEvent {
    data object Init : HomeEvent
    data class OnBookingRoomSuccess(val data: BookingQueueEntity) : HomeEvent
    data object OnBookingRoomError : HomeEvent
}