package com.pahnupan.poc.agora.presentation.video.composable

import android.view.SurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.pahnupan.poc.agora.composable.theme.Typography
import com.pahnupan.poc.agora.presentation.video.TelemedUiState


@Composable
fun RemoteSurface(
    modifier: Modifier,
    surfaceView: SurfaceView,
    remoteState: TelemedUiState.RemoteConnectionState

) {
    Box(modifier) {
        surfaceView.let { view ->
            AndroidView(
                factory = { view },
                modifier = Modifier.fillMaxSize()
            )
        }
        when (remoteState) {
            TelemedUiState.RemoteConnectionState.OnExit -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.2F)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("User Exit ...", color = Color.White)
                }
            }
            TelemedUiState.RemoteConnectionState.OnNotJoining -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Please wait User to Connect", color = Color.White , style = Typography.titleMedium)
                }
            }

            TelemedUiState.RemoteConnectionState.OnStable -> {
                Unit
            }
            TelemedUiState.RemoteConnectionState.OnBad -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.Black.copy(alpha = 0.2F)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("The Connection Lost.. ", color = Color.White)
                }
            }

        }


    }
}

@Preview
@Composable
private fun RemoteSurfacePreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        RemoteSurface(
            modifier = Modifier
                .fillMaxSize(),
            surfaceView = SurfaceView(LocalContext.current),
            remoteState = TelemedUiState.RemoteConnectionState.OnNotJoining
        )
    }
}

@Preview
@Composable
private fun RemoteSurfacePreviewOnStable() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        RemoteSurface(
            modifier = Modifier
                .fillMaxSize(),
            surfaceView = SurfaceView(LocalContext.current),
            remoteState = TelemedUiState.RemoteConnectionState.OnStable
        )
    }
}
@Preview
@Composable
private fun RemoteSurfacePreviewOnBad() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        RemoteSurface(
            modifier = Modifier
                .fillMaxSize(),
            surfaceView = SurfaceView(LocalContext.current),
            remoteState = TelemedUiState.RemoteConnectionState.OnBad
        )
    }
}

@Preview
@Composable
private fun RemoteSurfacePreviewOnExit() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        RemoteSurface(
            modifier = Modifier
                .fillMaxSize(),
            surfaceView = SurfaceView(LocalContext.current),
            remoteState = TelemedUiState.RemoteConnectionState.OnExit
        )
    }
}