package com.pahnupan.poc.agora.presentation.video.composable

import android.view.SurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pahnupan.poc.agora.presentation.video.TelemedUiState


@Composable
fun LocalSurface(
    modifier: Modifier,
    surfaceView: SurfaceView,
    enableCamera: Boolean,
    localState: TelemedUiState.LocalConnectionState
) {
    Box(modifier.clip(RoundedCornerShape(8.dp))) {
        surfaceView.let { view ->
            AndroidView(
                factory = { view },
                modifier = Modifier.fillMaxSize()
            )
        }

        when (localState) {
            TelemedUiState.LocalConnectionState.OnInit -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray),
                )
            }

            TelemedUiState.LocalConnectionState.OnJoined -> {
                Unit
            }
        }

        if (!enableCamera && localState is TelemedUiState.LocalConnectionState.OnJoined) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.2F)),
                contentAlignment = Alignment.Center
            ) {
                Text("กล้องปิดอยู่", color = Color.White)

            }
        }
    }
}

@Preview
@Composable
fun LocalPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LocalSurface(
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .padding(20.dp)
                .align(alignment = Alignment.TopEnd),
            surfaceView = SurfaceView(LocalContext.current),
            enableCamera = true,
            localState = TelemedUiState.LocalConnectionState.OnJoined
        )
    }
}

@Preview
@Composable
fun LocalPreviewDismissCamera() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LocalSurface(
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .padding(20.dp)
                .align(alignment = Alignment.TopEnd),
            surfaceView = SurfaceView(LocalContext.current),
            enableCamera = false,
            localState = TelemedUiState.LocalConnectionState.OnJoined

        )
    }
}

@Preview
@Composable
fun LocalPreviewDisable() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LocalSurface(
            modifier = Modifier
                .width(150.dp)
                .height(200.dp)
                .padding(20.dp)
                .align(alignment = Alignment.TopEnd),
            surfaceView = SurfaceView(LocalContext.current),
            enableCamera = false,
            localState = TelemedUiState.LocalConnectionState.OnInit
        )
    }
}