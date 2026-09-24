package com.example.service.wallpaper

/**
 * Architectural contract defining lifecycle methods for the Android Live Wallpaper Service.
 * Implemented when the Android WallpaperService is introduced in subsequent steps.
 */
interface LiveWallpaperServiceContract {
    fun onEngineCreated()
    fun onEngineDestroyed()
    fun onSurfaceCreated()
    fun onSurfaceChanged(width: Int, height: Int)
    fun onSurfaceDestroyed()
    fun onVisibilityChanged(isVisible: Boolean)
    fun onOffsetsChanged(xOffset: Float, yOffset: Float, xOffsetStep: Float, yOffsetStep: Float, xPixelOffset: Int, yPixelOffset: Int)
}
