package com.example.service.wallpaper

import android.media.MediaPlayer
import android.net.Uri
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import com.example.R
import com.example.data.local.LiveWallpaperPreferences

/**
 * Production Android Live Wallpaper Service.
 * Implements hardware-accelerated video rendering for wallpapers on Android Home & Lock screens.
 */
class LiveWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return VideoWallpaperEngine()
    }

    inner class VideoWallpaperEngine : WallpaperService.Engine(), LiveWallpaperServiceContract {
        private var mediaPlayer: MediaPlayer? = null
        private var isVisibleState = false
        private var surfaceHolderRef: SurfaceHolder? = null

        override fun onCreate(surfaceHolder: SurfaceHolder) {
            super.onCreate(surfaceHolder)
            this.surfaceHolderRef = surfaceHolder
            onEngineCreated()
        }

        override fun onEngineCreated() {
            Log.d(TAG, "VideoWallpaperEngine created")
        }

        override fun onEngineDestroyed() {
            Log.d(TAG, "VideoWallpaperEngine destroyed")
            releaseMediaPlayer()
        }

        override fun onSurfaceCreated(holder: SurfaceHolder) {
            super.onSurfaceCreated(holder)
            this.surfaceHolderRef = holder
            setupMediaPlayer(holder)
            onSurfaceCreated()
        }

        override fun onSurfaceCreated() {
            // Contract hook
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            this.surfaceHolderRef = holder
            onSurfaceChanged(width, height)
        }

        override fun onSurfaceChanged(width: Int, height: Int) {
            // Contract hook
        }

        override fun onSurfaceDestroyed(holder: SurfaceHolder) {
            super.onSurfaceDestroyed(holder)
            this.surfaceHolderRef = null
            releaseMediaPlayer()
            onSurfaceDestroyed()
        }

        override fun onSurfaceDestroyed() {
            // Contract hook
        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            this.isVisibleState = visible
            if (visible) {
                if (mediaPlayer == null && surfaceHolderRef != null) {
                    setupMediaPlayer(surfaceHolderRef!!)
                } else {
                    try {
                        mediaPlayer?.start()
                    } catch (e: Exception) {
                        Log.e(TAG, "Error resuming playback", e)
                    }
                }
            } else {
                try {
                    mediaPlayer?.pause()
                } catch (e: Exception) {
                    Log.e(TAG, "Error pausing playback", e)
                }
            }
        }

        override fun onOffsetsChanged(
            xOffset: Float,
            yOffset: Float,
            xOffsetStep: Float,
            yOffsetStep: Float,
            xPixelOffset: Int,
            yPixelOffset: Int
        ) {
            // Optional parallax offset handling
        }

        private fun setupMediaPlayer(holder: SurfaceHolder) {
            releaseMediaPlayer()
            try {
                val context = applicationContext
                val activeId = LiveWallpaperPreferences.getActiveWallpaperId(context)
                val customUriString = LiveWallpaperPreferences.getActiveWallpaperUri(context)

                val videoUri: Uri = if (!customUriString.isNullOrEmpty()) {
                    Uri.parse(customUriString)
                } else {
                    val rawResId = when (activeId) {
                        "anime_shadow_monarch" -> R.raw.anime_shadow_monarch
                        else -> R.raw.bmw_m2_1000hp
                    }
                    Uri.parse("android.resource://${context.packageName}/$rawResId")
                }

                val player = MediaPlayer().apply {
                    setSurface(holder.surface)
                    setDataSource(context, videoUri)
                    isLooping = true

                    val audioEnabled = LiveWallpaperPreferences.isAudioEnabled(context)
                    val vol = if (audioEnabled) 1.0f else 0.0f
                    setVolume(vol, vol)

                    setOnPreparedListener { mp ->
                        if (isVisibleState) {
                            mp.start()
                        }
                    }

                    setOnErrorListener { _, what, extra ->
                        Log.e(TAG, "MediaPlayer error: what=$what extra=$extra")
                        true
                    }

                    prepareAsync()
                }
                mediaPlayer = player
            } catch (e: Exception) {
                Log.e(TAG, "Error preparing video wallpaper MediaPlayer", e)
            }
        }

        private fun releaseMediaPlayer() {
            try {
                mediaPlayer?.apply {
                    if (isPlaying) {
                        stop()
                    }
                    reset()
                    release()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error releasing MediaPlayer", e)
            } finally {
                mediaPlayer = null
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            onEngineDestroyed()
        }
    }

    companion object {
        private const val TAG = "LiveWallpaperService"
    }
}
