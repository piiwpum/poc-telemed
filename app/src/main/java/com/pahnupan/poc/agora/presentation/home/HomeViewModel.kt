package com.pahnupan.poc.agora.presentation.home

import android.Manifest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.domain.usecase.BookingQueueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val createRoomUseCase: BookingQueueUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(HomeUiState(string = ""))
    val event = Channel<HomeEvent>()
    private val intent = MutableSharedFlow<HomeIntent>()

    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    init {
        handleIntent()
    }

    fun emitIntent(intent: HomeIntent) {
        viewModelScope.launch(Dispatchers.IO) {
            this@HomeViewModel.intent.emit(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch(Dispatchers.IO) {
            intent.collectLatest {
                when (it) {
                    HomeIntent.Init -> {
                        Unit
                    }

                    HomeIntent.BookingQueue -> {
                        bookingQueue()
                    }

                    else -> Unit
                }
            }
        }
    }


    private fun bookingQueue() {
        viewModelScope.launch(Dispatchers.IO) {
            createRoomUseCase.invoke()
                .onStart {
                    uiState.update { it.copy(loading = true) }
                }
                .collect { data ->
                    uiState.update { it.copy(loading = false) }
                    if (data is NetworkResult.Success) {
                        event.send(HomeEvent.OnBookingRoomSuccess(data.data))
                    } else {
                        event.send(HomeEvent.OnBookingRoomError)
                    }
                }
        }
    }
}