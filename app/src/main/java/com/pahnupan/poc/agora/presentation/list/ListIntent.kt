package com.pahnupan.poc.agora.presentation.list


sealed interface ListIntent {
    data object Init : ListIntent
    data object Refresh : ListIntent
}