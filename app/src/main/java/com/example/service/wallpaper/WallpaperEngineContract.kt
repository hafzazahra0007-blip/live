package com.example.service.wallpaper

import android.view.SurfaceHolder

/**
 * Architectural contract defining playback, rendering, and lifecycle capabilities
 * for the Live Wallpaper rendering engine.
 */
interface WallpaperEngineContract {
    fun attachSurface(surfaceHolder: SurfaceHolder)
    fun detachSurface()
    fun loadSource(uri: String)
    fun play()
    fun pause()
    fun setVolume(volume: Float)
    fun isPlaying(): Boolean
    fun release()
}
