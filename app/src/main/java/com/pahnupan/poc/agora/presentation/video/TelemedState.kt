package com.pahnupan.poc.agora.presentation.video

import android.view.SurfaceView


data class TelemedUiState(
    val enableCamera: Boolean = true,
    val enableMic: Boolean = true,
    val localSurfaceView: SurfaceView? = null,
    val remoteSurfaceView: SurfaceView? = null,
    val localConnectionState: LocalConnectionState,
    val remoteConnectionState: RemoteConnectionState
) {
    sealed interface LocalConnectionState {
        data object OnNotJoining : LocalConnectionState
        data object OnStable : LocalConnectionState
        data object OnReconnect : LocalConnectionState
        data object OnFail : LocalConnectionState
    }

    sealed interface RemoteConnectionState {
        data object OnNotJoining : RemoteConnectionState
        data object OnStable : RemoteConnectionState
        data object OnBad : RemoteConnectionState
    }
}

sealed interface TelemedEvent {
    data object Init : TelemedEvent
    data class OnJoined(val uid: Int) : TelemedEvent
    data object OnJoinError : TelemedEvent
    data class OnUserJoined(val uid: Int) : TelemedEvent
    data class OnUserOffline(val uid: Int) : TelemedEvent
}