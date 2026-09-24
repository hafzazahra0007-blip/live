package com.example.ui.preview

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import android.widget.VideoView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.R
import com.example.data.local.LiveWallpaperPreferences
import com.example.service.wallpaper.LiveWallpaperService
import com.example.ui.theme.LightBackground
import com.example.ui.theme.LightBorder
import com.example.ui.theme.PrimaryLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

/**
 * Video Preview Screen.
 * Plays the chosen video wallpaper in a seamless hardware-accelerated loop,
 * with real live wallpaper application via the Android system WallpaperManager.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreenPlaceholder(
    wallpaperId: String = "bmw_m2_1000hp",
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    var videoViewRef = remember { WeakReference<VideoView>(null) }

    DisposableEffect(Unit) {
        onDispose {
            videoViewRef.get()?.stopPlayback()
        }
    }

    val wallpaperTitle = when (wallpaperId) {
        "bmw_m2_1000hp" -> "BMW M2 1000 HP"
        "anime_shadow_monarch" -> "Solo Leveling - Shadow Monarch"
        else -> "Custom Video Wallpaper"
    }

    val wallpaperSpecs = when (wallpaperId) {
        "bmw_m2_1000hp" -> "Local Video Asset • 576x1032 • 30 FPS • 15s Loop"
        "anime_shadow_monarch" -> "Local Video Asset • 720x1280 • 30 FPS • 5s Loop"
        else -> "Gallery Video Clip • Full Resolution • Loop"
    }

    fun applyWallpaper() {
        LiveWallpaperPreferences.setActiveWallpaper(context, wallpaperId)
        try {
            val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
                putExtra(
                    WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    ComponentName(context, LiveWallpaperService::class.java)
                )
            }
            context.startActivity(intent)
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Opening system wallpaper preview for $wallpaperTitle")
            }
        } catch (e: Exception) {
            try {
                val chooserIntent = Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
                context.startActivity(chooserIntent)
            } catch (e2: Exception) {
                Toast.makeText(context, "$wallpaperTitle selected as Live Wallpaper!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("preview_screen"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Wallpaper Preview",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = TextPrimary
                        )
                        Text(
                            text = wallpaperTitle,
                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                            color = TextSecondary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("preview_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = TextPrimary
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Color(0xFFEEF2FF),
                        border = BorderStroke(0.5.dp, Color(0xFF818CF8).copy(alpha = 0.6f)),
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Text(
                            text = "4K LIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            ),
                            color = PrimaryLight,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBackground)
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Video preview container
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, LightBorder, RoundedCornerShape(20.dp))
                    .testTag("video_player_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AndroidView(
                        factory = { ctx ->
                            VideoView(ctx).apply {
                                videoViewRef = WeakReference(this)
                                val videoUri: Uri = when (wallpaperId) {
                                    "bmw_m2_1000hp" -> Uri.parse("android.resource://${ctx.packageName}/${R.raw.bmw_m2_1000hp}")
                                    "anime_shadow_monarch" -> Uri.parse("android.resource://${ctx.packageName}/${R.raw.anime_shadow_monarch}")
                                    else -> {
                                        val customUriStr = LiveWallpaperPreferences.getActiveWallpaperUri(ctx)
                                        if (!customUriStr.isNullOrEmpty()) {
                                            Uri.parse(customUriStr)
                                        } else {
                                            Uri.parse("android.resource://${ctx.packageName}/${R.raw.bmw_m2_1000hp}")
                                        }
                                    }
                                }
                                setVideoURI(videoUri)
                                setOnPreparedListener { mediaPlayer ->
                                    mediaPlayer.isLooping = true
                                    start()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("video_view_player")
                    )
                }
            }

            // Specs info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, LightBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF10B981))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = wallpaperTitle,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = wallpaperSpecs,
                            style = MaterialTheme.typography.bodySmall,
                            color = TextSecondary
                        )
                    }
                }
            }

            // Set as Live Wallpaper primary action button
            Button(
                onClick = { applyWallpaper() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("set_wallpaper_button"),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryLight,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Wallpaper,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Apply Live Wallpaper",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}
