package com.pahnupan.poc.agora.presentation.video

import io.agora.rtc.RtcEngine


data class TelemedUiState(
    val enableCamera: Boolean = true,
    val enableMic: Boolean = true,
    val localConnectionState: LocalConnectionState,
    val remoteConnectionState: RemoteConnectionState
) {
    sealed interface LocalConnectionState {
        data object OnInit : LocalConnectionState
        data object OnJoined : LocalConnectionState
    }

    sealed interface RemoteConnectionState {
        data object OnNotJoining : RemoteConnectionState
        data object OnStable : RemoteConnectionState
        data object OnBad : RemoteConnectionState
        data object OnExit : RemoteConnectionState
    }
}

sealed interface TelemedEvent {
    data object Init : TelemedEvent
    data class InitAgoraSuccess(val rtcEngine : RtcEngine?) : TelemedEvent
    data class OnJoined(val uid: Int) : TelemedEvent
    data object OnJoinError : TelemedEvent
    data class OnUserJoined(val rtcEngine : RtcEngine? , val uid: Int) : TelemedEvent
    data class OnUserOffline(val uid: Int) : TelemedEvent
}