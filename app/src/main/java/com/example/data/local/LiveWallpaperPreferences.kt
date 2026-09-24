package com.example.data.local

import android.content.Context
import android.content.SharedPreferences

object LiveWallpaperPreferences {
    private const val PREFS_NAME = "live_wallpaper_prefs"
    private const val KEY_ACTIVE_WALLPAPER_ID = "active_wallpaper_id"
    private const val KEY_ACTIVE_WALLPAPER_URI = "active_wallpaper_uri"
    private const val KEY_AUDIO_ENABLED = "audio_enabled"
    private const val KEY_BATTERY_SAVER = "battery_saver"
    private const val KEY_HARDWARE_ACCEL = "hardware_acceleration"

    const val DEFAULT_WALLPAPER_ID = "bmw_m2_1000hp"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getActiveWallpaperId(context: Context): String {
        return getPrefs(context).getString(KEY_ACTIVE_WALLPAPER_ID, DEFAULT_WALLPAPER_ID)
            ?: DEFAULT_WALLPAPER_ID
    }

    fun setActiveWallpaper(context: Context, wallpaperId: String, customUri: String? = null) {
        getPrefs(context).edit()
            .putString(KEY_ACTIVE_WALLPAPER_ID, wallpaperId)
            .putString(KEY_ACTIVE_WALLPAPER_URI, customUri)
            .apply()
    }

    fun getActiveWallpaperUri(context: Context): String? {
        return getPrefs(context).getString(KEY_ACTIVE_WALLPAPER_URI, null)
    }

    fun isAudioEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_AUDIO_ENABLED, false)
    }

    fun setAudioEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_AUDIO_ENABLED, enabled).apply()
    }

    fun isBatterySaverEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_BATTERY_SAVER, true)
    }

    fun setBatterySaverEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_BATTERY_SAVER, enabled).apply()
    }

    fun isHardwareAccelEnabled(context: Context): Boolean {
        return getPrefs(context).getBoolean(KEY_HARDWARE_ACCEL, true)
    }

    fun setHardwareAccelEnabled(context: Context, enabled: Boolean) {
        getPrefs(context).edit().putBoolean(KEY_HARDWARE_ACCEL, enabled).apply()
    }
}
