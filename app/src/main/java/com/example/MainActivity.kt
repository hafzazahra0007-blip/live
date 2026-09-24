package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.core.navigation.Screen
import com.example.data.model.WallpaperCategory
import com.example.ui.categories.CategoriesScreenPlaceholder
import com.example.ui.home.HomeScreen
import com.example.ui.myvideos.MyVideosScreenPlaceholder
import com.example.ui.preview.PreviewScreenPlaceholder
import com.example.ui.settings.SettingsScreenPlaceholder
import com.example.ui.theme.LiveWallpaperTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiveWallpaperTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LiveWallpaperAppNavHost()
                }
            }
        }
    }
}

@Composable
fun LiveWallpaperAppNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onOpenSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onOpenCategory = { category ->
                    when (category) {
                        WallpaperCategory.ANIME -> navController.navigate(Screen.AnimeWallpapers.route)
                        WallpaperCategory.NATURE -> navController.navigate(Screen.NatureWallpapers.route)
                        WallpaperCategory.CARS -> navController.navigate(Screen.CarsWallpapers.route)
                        WallpaperCategory.MY_VIDEOS -> navController.navigate(Screen.MyVideos.route)
                    }
                }
            )
        }

        composable(route = Screen.AnimeWallpapers.route) {
            CategoriesScreenPlaceholder(
                category = WallpaperCategory.ANIME,
                onBack = { navController.popBackStack() },
                onOpenWallpaper = { wallpaperId ->
                    navController.navigate(Screen.VideoPreview.createRoute(wallpaperId))
                }
            )
        }

        composable(route = Screen.NatureWallpapers.route) {
            CategoriesScreenPlaceholder(
                category = WallpaperCategory.NATURE,
                onBack = { navController.popBackStack() },
                onOpenWallpaper = { wallpaperId ->
                    navController.navigate(Screen.VideoPreview.createRoute(wallpaperId))
                }
            )
        }

        composable(route = Screen.CarsWallpapers.route) {
            CategoriesScreenPlaceholder(
                category = WallpaperCategory.CARS,
                onBack = { navController.popBackStack() },
                onOpenWallpaper = { wallpaperId ->
                    navController.navigate(Screen.VideoPreview.createRoute(wallpaperId))
                }
            )
        }

        composable(route = Screen.MyVideos.route) {
            MyVideosScreenPlaceholder(
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.VideoPreview.route,
            arguments = listOf(
                navArgument("wallpaperId") {
                    type = NavType.StringType
                    defaultValue = "bmw_m2_1000hp"
                }
            )
        ) { backStackEntry ->
            val wallpaperId = backStackEntry.arguments?.getString("wallpaperId") ?: "bmw_m2_1000hp"
            PreviewScreenPlaceholder(
                wallpaperId = wallpaperId,
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = Screen.Settings.route) {
            SettingsScreenPlaceholder(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
