package com.pahnupan.poc.agora.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pahnupan.poc.agora.data.helper.NetworkResult
import com.pahnupan.poc.agora.domain.usecase.GetQueuesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getQueuesUseCase: GetQueuesUseCase
) : ViewModel() {

    val uiState = MutableStateFlow<ListUiState>(ListUiState.Loading)
    val intent = MutableSharedFlow<ListIntent>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collectLatest {
                when (it) {
                    ListIntent.Init -> {
                        getQueues()
                    }

                    ListIntent.Refresh -> {
                        getQueues()
                    }
                }
            }
        }
    }

    fun emitIntent(action: ListIntent) {
        viewModelScope.launch { intent.emit(action) }
    }

    private suspend fun getQueues() {
        getQueuesUseCase.invoke()
            .onStart {
                uiState.update { ListUiState.Loading }
            }
            .collectLatest { response ->
                if (response is NetworkResult.Success) {
                    if (response.data.isNotEmpty()) {
                        uiState.update { ListUiState.Success(data = response.data) }
                    } else {
                        uiState.update { ListUiState.Empty }
                    }
                } else {
                    uiState.update { ListUiState.Error }
                }
            }
    }
}