package com.example.core.navigation

/**
 * Scalable navigation route definitions for all features planned in the app:
 * - Home screen
 * - Nature wallpapers category
 * - Cars wallpapers category
 * - Anime wallpapers category
 * - My Videos (local video library)
 * - Video Preview
 * - Video Trimmer
 * - Settings
 */
sealed class Screen(val route: String, val title: String) {
    data object Home : Screen("home", "Home")
    data object NatureWallpapers : Screen("category/nature", "Nature Wallpapers")
    data object CarsWallpapers : Screen("category/cars", "Cars Wallpapers")
    data object AnimeWallpapers : Screen("category/anime", "Anime Wallpapers")
    data object MyVideos : Screen("my_videos", "My Videos")
    data object VideoPreview : Screen("preview/{wallpaperId}", "Wallpaper Preview") {
        fun createRoute(wallpaperId: String): String = "preview/$wallpaperId"
    }
    data object VideoTrimmer : Screen("trimmer", "Video Trimmer")
    data object Settings : Screen("settings", "Settings")
}
