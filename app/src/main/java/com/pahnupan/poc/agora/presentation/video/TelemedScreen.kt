package com.pahnupan.poc.agora.presentation.video

import android.content.Context
import android.view.SurfaceView
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.MicOff
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Videocam
import androidx.compose.material.icons.rounded.VideocamOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pahnupan.poc.agora.NavigateTelemedScreen
import com.pahnupan.poc.agora.presentation.video.composable.LocalSurface
import com.pahnupan.poc.agora.presentation.video.composable.RemoteSurface
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import kotlinx.coroutines.flow.receiveAsFlow


@Composable
internal fun TelemedRoute(
    viewModel: TelemedViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    bundle: NavigateTelemedScreen,
) {
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
    val event = viewModel.event.receiveAsFlow().collectAsState(TelemedEvent.Init).value
    val localSurfaceView: SurfaceView = remember { RtcEngine.CreateRendererView(context) }
    val remoteSurfaceView: SurfaceView = remember { RtcEngine.CreateRendererView(context) }


    LaunchedEffect(true) {
        viewModel.emitIntent(TelemedIntent.InitAgora(context, bundle))
    }

    LaunchedEffect(event) {
        when (event) {
            TelemedEvent.Init -> {
            }

            is TelemedEvent.InitAgoraSuccess -> {
                event.rtcEngine?.setupLocalVideo(
                    VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0)
                )
            }

            TelemedEvent.OnJoinError -> {
                showToast(context, "join channel error")
            }

            is TelemedEvent.OnJoined -> {
                showToast(context, "please wait user coming")
            }

            is TelemedEvent.OnUserJoined -> {
                event.rtcEngine?.setupRemoteVideo(
                    VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, event.uid)
                )
                showToast(context, "user join to channel")
            }

            is TelemedEvent.OnUserOffline -> {
                showToast(context, "user offline")
            }

        }
    }

    DisposableEffect(Unit) {
        onDispose {
            viewModel.emitIntent(TelemedIntent.LeaveChannel)
        }
    }

    BackHandler {
        viewModel.emitIntent(TelemedIntent.LeaveChannel)
        navController.popBackStack()
    }

    TelemedScreen(
        localView = localSurfaceView,
        remoteView = remoteSurfaceView,
        enableMic = uiState.enableMic,
        enableCam = uiState.enableCamera,
        onClickEnableMic = {
            viewModel.emitIntent(TelemedIntent.ToggleMic)
        },
        onClickEnableCam = {
            viewModel.emitIntent(TelemedIntent.ToggleCamera)
        },
        onClickClose = {
            viewModel.emitIntent(TelemedIntent.LeaveChannel)
            navController.popBackStack()
        })
}

private fun showToast(context: Context, mess: String) {
    Toast.makeText(context, mess, Toast.LENGTH_SHORT).show()
}

@Composable
private fun TelemedScreen(
    localView: SurfaceView,
    remoteView: SurfaceView,
    uiState: TelemedUiState = TelemedUiState(
        enableCamera = false,
        enableMic = false,
        localConnectionState = TelemedUiState.LocalConnectionState.OnInit,
        remoteConnectionState = TelemedUiState.RemoteConnectionState.OnNotJoining
    ),
    enableMic: Boolean = true,
    enableCam: Boolean = true,
    onClickEnableMic: () -> Unit = {},
    onClickEnableCam: () -> Unit = {},
    onClickClose: () -> Unit = {}

) {
    Scaffold() { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                )
        ) {
            RemoteSurface(
                modifier = Modifier
                    .zIndex(1f)
                    .fillMaxSize()
                    .align(alignment = Alignment.Center),
                surfaceView = remoteView,
                remoteState = uiState.remoteConnectionState
            )
            LocalSurface(
                modifier = Modifier
                    .zIndex(2f)
                    .width(150.dp)
                    .height(200.dp)
                    .padding(20.dp)
                    .align(alignment = Alignment.TopEnd),
                surfaceView = localView,
                enableCamera = enableCam,
                localState = uiState.localConnectionState
            )

            Row(
                modifier = Modifier
                    .zIndex(3f)
                    .padding(24.dp)
                    .fillMaxWidth()
                    .align(alignment = Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(color = Color.White),
                    onClick = {
                        if (uiState.localConnectionState is TelemedUiState.LocalConnectionState.OnJoined) {
                            onClickEnableMic.invoke()
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = if (enableMic) Icons.Rounded.Mic else Icons.Rounded.MicOff,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
                IconButton(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(color = Color.Red),
                    onClick = onClickClose
                ) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Rounded.Phone,
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                IconButton(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(color = Color.White),
                    onClick = {
                        if (uiState.localConnectionState is TelemedUiState.LocalConnectionState.OnJoined) {
                            onClickEnableCam.invoke()
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = if (enableCam) Icons.Rounded.Videocam else Icons.Rounded.VideocamOff,
                        contentDescription = "",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun TelemedScreenPreview() {
    TelemedScreen(
        localView = SurfaceView(LocalContext.current),
        remoteView = SurfaceView(LocalContext.current),
    )
}