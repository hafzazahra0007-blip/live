# Live Wallpaper (BMW & Anime Motion Engine)

A modern Android Live Wallpaper application built with Kotlin, Jetpack Compose, and Material Design 3. Powered by an Android `WallpaperService` engine providing hardware-accelerated 60 FPS video wallpaper playback for Android home and lock screens.

## Core Features

- **BMW M2 1000 HP Live Wallpaper**: High-octane video wallpaper featuring dynamic drift and night driving visuals.
- **Solo Leveling - Shadow Monarch Anime Wallpaper**: Ultra HD anime live wallpaper with seamless loop.
- **Android Live Wallpaper Service**: Full integration with Android `WallpaperService` and `WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER`.
- **Seamless Video Looping**: Hardware-accelerated surface rendering with automatic background pausing to conserve battery life.
- **My Videos (Custom Video Picker)**: Zero-permission Android Photo/Video Picker (`ActivityResultContracts.PickVisualMedia`) compliant with Google Play policy, allowing users to import any video from their device gallery and set it as their live wallpaper.
- **Customizable Preferences**:
  - Live Wallpaper sound output toggle (audio mute / sound).
  - Battery Saver Mode (automatic pause during low power).
  - Hardware Acceleration toggle.
- **Material 3 Design System**: Dynamic cards, status badges, fluid responsive typography, and navigation.

## Architecture

- **UI**: Jetpack Compose, Material 3, Navigation Compose.
- **Service**: `com.example.service.wallpaper.LiveWallpaperService` registered in `AndroidManifest.xml` with `android.permission.BIND_WALLPAPER`.
- **Data & Local Storage**: `LiveWallpaperPreferences` backed by `SharedPreferences` for active wallpaper state and playback preferences.
- **Video Playback**: Native `MediaPlayer` surface rendering inside `WallpaperService.Engine` and `VideoView` for in-app preview.
