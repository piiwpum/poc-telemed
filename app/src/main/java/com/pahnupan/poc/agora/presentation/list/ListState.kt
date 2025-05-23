package com.pahnupan.poc.agora.presentation.list

import com.pahnupan.poc.agora.domain.entries.QueueEntity


sealed interface ListUiState {
    data object Loading : ListUiState
    data object Empty : ListUiState
    data object Error : ListUiState
    data class Success(val data: List<QueueEntity>) : ListUiState
}