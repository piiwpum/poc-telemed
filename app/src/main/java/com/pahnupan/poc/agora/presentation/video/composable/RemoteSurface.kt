package com.pahnupan.poc.agora.presentation.video.composable

import android.view.SurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex


@Composable
fun RemoteSurface(
    modifier: Modifier,
    surfaceView: SurfaceView?,
    
) {
    Box(modifier) {
        surfaceView?.let { view ->
            AndroidView(
                factory = { view },
                modifier = Modifier.fillMaxSize()
            )
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
                .align(alignment = Alignment.TopEnd)
        )
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
            surfaceView = null
        )
    }
}