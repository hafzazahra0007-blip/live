package com.example.data.model

/**
 * Data contract for custom video import, trim boundaries, and playback configurations.
 */
data class VideoClipInfo(
    val sourceUri: String,
    val startMs: Long = 0L,
    val endMs: Long = 0L,
    val isLooping: Boolean = true,
    val isMuted: Boolean = true,
    val scaleMode: VideoScaleMode = VideoScaleMode.CENTER_CROP
)

enum class VideoScaleMode {
    CENTER_CROP,
    FIT_CENTER
}
