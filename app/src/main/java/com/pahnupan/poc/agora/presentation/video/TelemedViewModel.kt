package com.pahnupan.poc.agora.presentation.video

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pahnupan.poc.agora.APP_ID
import com.pahnupan.poc.agora.TAG
import com.pahnupan.poc.agora.TOKEN
import com.pahnupan.poc.agora.core.SharePref
import com.pahnupan.poc.agora.presentation.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.RtcEngineConfig
import io.agora.rtc.video.VideoEncoderConfiguration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TelemedViewModel @Inject constructor(
    private val sharePref: SharePref
) : ViewModel() {
    val test = 1

    private var remoteUuid = 0

    var rtcEngine: RtcEngine? = null

    val uiState = MutableStateFlow(
        TelemedUiState(
            enableCamera = false,
            enableMic = false,
            localConnectionState = TelemedUiState.LocalConnectionState.OnInit,
            remoteConnectionState = TelemedUiState.RemoteConnectionState.OnNotJoining,
        )
    )
    val event = Channel<TelemedEvent>()

    private val intent = MutableSharedFlow<TelemedIntent>()

    private val rtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            Log.d(TAG, "channel:$channel,uid:$uid,elapsed:$elapsed")
            viewModelScope.launch {
                uiState.update {
                    it.copy(localConnectionState = TelemedUiState.LocalConnectionState.OnJoined)
                }
                event.send(TelemedEvent.OnJoined(uid))
            }
        }

        override fun onError(err: Int) {
            Log.d(TAG, "onError $err")
            super.onError(err)
            viewModelScope.launch {
                event.send(TelemedEvent.OnJoinError)
            }
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            Log.d(TAG, "onUserJoined:$uid")
            viewModelScope.launch {
                event.send(TelemedEvent.OnUserJoined(rtcEngine, uid))
                uiState.update { it.copy(remoteConnectionState = TelemedUiState.RemoteConnectionState.OnStable) }
            }
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.d(TAG, "onUserOffline:${uid}")
            viewModelScope.launch {
                event.send(TelemedEvent.OnUserOffline(uid))
                uiState.update { it.copy(remoteConnectionState = TelemedUiState.RemoteConnectionState.OnExit) }
            }
            super.onUserOffline(uid, reason)
        }

        override fun onConnectionLost() {
            Log.d(TAG, "connect lost !")
            super.onConnectionLost()
        }

        override fun onConnectionStateChanged(state: Int, reason: Int) {
            when (state) {
                Constants.CONNECTION_STATE_RECONNECTING -> {
                    Log.d(TAG, "CONNECTION_STATE_RECONNECTING")
                }

                Constants.CONNECTION_STATE_CONNECTED -> {
                    Log.d(TAG, "CONNECTION_STATE_CONNECTED")

                }

                Constants.CONNECTION_STATE_FAILED -> {
                    Log.d(TAG, "CONNECTION_STATE_FAILED")
                }
            }
            super.onConnectionStateChanged(state, reason)
        }

        override fun onNetworkQuality(uid: Int, txQuality: Int, rxQuality: Int) {
            if (uid == 0) {
                Log.d("Agora", "Local tx: $txQuality, rx: $rxQuality")
            } else {
                Log.d("Agora", "Remote uid=$uid tx: $txQuality, rx: $rxQuality")
            }
        }
    }

    init {
        handleIntent()
    }

    fun emitIntent(intent: TelemedIntent) {
        viewModelScope.launch {
            this@TelemedViewModel.intent.emit(intent)
        }
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.collectLatest {
                when (it) {
                    TelemedIntent.Init -> {}

                    is TelemedIntent.InitAgora -> {
                        onInitRtcEngine(
                            context = it.context,
                            isHost = sharePref.role == LoginUiState.Role.DOCTOR.name
                        )
                    }

                    TelemedIntent.ToggleCamera -> onToggleCamera()

                    TelemedIntent.LeaveChannel -> leaveChannel()

                    TelemedIntent.ToggleMic -> onToggleMic()

                    TelemedIntent.SwitchCamera -> onSwitchCamera()

                }
            }
        }
    }

    private fun onInitRtcEngine(context: Context, isHost: Boolean) {
        RtcEngineConfig().apply {
            mContext = context
            mAppId = APP_ID
            mEventHandler = rtcEventHandler
        }.also {
            rtcEngine = RtcEngine.create(it)
        }
        rtcEngine?.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)
        rtcEngine?.enableVideo()

        val videoConfig = VideoEncoderConfiguration(
            VideoEncoderConfiguration.VideoDimensions(1920, 1080),
            VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_30,
            VideoEncoderConfiguration.STANDARD_BITRATE,
            VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
        )
        rtcEngine?.setVideoEncoderConfiguration(videoConfig)

        // TODO: change to roll
        if (isHost) {
            rtcEngine?.setClientRole(Constants.CLIENT_ROLE_BROADCASTER)
        } else {
            rtcEngine?.setClientRole(Constants.CLIENT_ROLE_AUDIENCE)
        }
        viewModelScope.launch {
            event.send(TelemedEvent.InitAgoraSuccess(rtcEngine))
            rtcEngine?.startPreview()
            rtcEngine?.joinChannel(TOKEN, "test_telemed", "", sharePref.uid ?: 0)
        }
    }

    private fun onToggleCamera() {
        val current = uiState.value.enableCamera
        rtcEngine?.muteLocalVideoStream(current)
        uiState.update { it.copy(enableCamera = !current) }

    }

    private fun onToggleMic() {
        val current = uiState.value.enableMic
        rtcEngine?.muteLocalAudioStream(current)
        uiState.update { it.copy(enableMic = !current) }
    }

    private fun onSwitchCamera() {
        rtcEngine?.switchCamera()
    }

    private fun leaveChannel() {
        rtcEngine?.leaveChannel()
        rtcEngine?.stopPreview()
        RtcEngine.destroy()
        rtcEngine = null
    }

}