package com.example.ui.trimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Foundation placeholder for Video Trimming screen.
 * Will host range slider, video timeline thumbnails, start/end marker controls, and preview.
 */
@Composable
fun TrimmerScreenPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Video Trimmer (Foundation Ready)")
    }
}
