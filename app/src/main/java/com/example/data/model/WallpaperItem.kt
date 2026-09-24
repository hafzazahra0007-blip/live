package com.example.data.model

/**
 * Core model contract for wallpaper items.
 * Prepared for local asset video files, trimmed clips, and future remote online wallpaper catalogs.
 */
data class WallpaperItem(
    val id: String,
    val title: String,
    val category: WallpaperCategory,
    val videoUri: String? = null,
    val thumbnailUri: String? = null,
    val durationMs: Long = 0L,
    val isLocal: Boolean = true,
    val description: String = ""
)
