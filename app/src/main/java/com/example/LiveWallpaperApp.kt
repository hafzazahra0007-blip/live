package com.example

import android.app.Application

/**
 * Application class for the Live Wallpaper project.
 * Serves as the central application lifecycle and configuration point.
 */
class LiveWallpaperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Foundation initialized. App-level services, crash reporting,
        // and future dependency injection components will be initialized here.
    }
}
