package com.pahnupan.poc.agora.presentation.home

sealed interface HomeIntent {
    data object Init : HomeIntent
    data object BookingQueue : HomeIntent
}