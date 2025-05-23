package com.pahnupan.poc.agora.presentation.video

import android.content.Context
import com.pahnupan.poc.agora.NavigateTelemedScreen


sealed interface TelemedIntent {
    data object Init : TelemedIntent
    data class InitAgora(val context: Context , val bundle : NavigateTelemedScreen) : TelemedIntent
    data object ToggleMic : TelemedIntent
    data object ToggleCamera : TelemedIntent
    data object SwitchCamera : TelemedIntent
    data object LeaveChannel : TelemedIntent
}