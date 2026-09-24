package com.example.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Forest
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.WallpaperCategory
import com.example.ui.theme.LightBackground
import com.example.ui.theme.LightBorder
import com.example.ui.theme.PrimaryLight
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

/**
 * Compact Home Screen for Android Live Wallpaper application.
 * Contains only the top header (app title, icon, settings) and wallpaper categories.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenSettings: () -> Unit,
    onOpenCategory: (WallpaperCategory) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LightBackground)
            .testTag("home_screen")
    ) {
        HomeTopBar(onOpenSettings = onOpenSettings)

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                CategoryCardsGrid(onCategoryClick = onOpenCategory)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    onOpenSettings: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.linearGradient(
                                colors = listOf(PrimaryLight, Color(0xFF3B82F6))
                            )
                        )
                        .border(
                            1.dp,
                            PrimaryLight.copy(alpha = 0.2f),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Wallpaper,
                        contentDescription = "App Logo",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 0.5.sp
                            ),
                            color = TextPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = Color(0xFFEEF2FF),
                            border = BorderStroke(
                                0.5.dp,
                                Color(0xFF818CF8).copy(alpha = 0.6f)
                            )
                        ) {
                            Text(
                                text = "4K LIVE",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                ),
                                color = PrimaryLight,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                    Text(
                        text = "Cinematic Motion Engine",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                        color = TextSecondary
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onOpenSettings,
                modifier = Modifier
                    .size(48.dp)
                    .testTag("home_settings_button")
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.dp, LightBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Open Settings",
                        tint = Color(0xFF334155),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier.testTag("home_top_bar")
    )
}

data class CategoryUiModel(
    val category: WallpaperCategory,
    val title: String,
    val subtitle: String,
    val tag: String,
    val countLabel: String,
    val backgroundColors: List<Color>,
    val borderColor: Color,
    val tagBackgroundColor: Color,
    val tagBorderColor: Color,
    val tagTextColor: Color,
    val iconBackgroundColor: Color,
    val titleColor: Color,
    val subtitleColor: Color,
    val countColor: Color,
    val icon: ImageVector
)

@Composable
private fun CategoryCardsGrid(
    onCategoryClick: (WallpaperCategory) -> Unit
) {
    val categories = listOf(
        CategoryUiModel(
            category = WallpaperCategory.NATURE,
            title = "Nature",
            subtitle = "Waterfalls, Aurora, Galaxy",
            tag = "POPULAR",
            countLabel = "48 Wallpapers",
            backgroundColors = listOf(Color(0xFFF0FDF4), Color(0xFFDCFCE7)),
            borderColor = Color(0xFFBBF7D0),
            tagBackgroundColor = Color(0xFFDCFCE7),
            tagBorderColor = Color(0xFF86EFAC),
            tagTextColor = Color(0xFF166534),
            iconBackgroundColor = Color(0xFF10B981),
            titleColor = Color(0xFF064E3B),
            subtitleColor = Color(0xFF15803D),
            countColor = Color(0xFF059669),
            icon = Icons.Default.Forest
        ),
        CategoryUiModel(
            category = WallpaperCategory.CARS,
            title = "Cars",
            subtitle = "Supercars, Drift, Night Drive",
            tag = "TRENDING",
            countLabel = "36 Wallpapers",
            backgroundColors = listOf(Color(0xFFFEF2F2), Color(0xFFFEE2E2)),
            borderColor = Color(0xFFFECACA),
            tagBackgroundColor = Color(0xFFFEE2E2),
            tagBorderColor = Color(0xFFFCA5A5),
            tagTextColor = Color(0xFF991B1B),
            iconBackgroundColor = Color(0xFFEF4444),
            titleColor = Color(0xFF7F1D1D),
            subtitleColor = Color(0xFFB91C1C),
            countColor = Color(0xFFDC2626),
            icon = Icons.Default.Speed
        ),
        CategoryUiModel(
            category = WallpaperCategory.ANIME,
            title = "Anime",
            subtitle = "Cyberpunk, Sci-Fi, Aesthetic",
            tag = "HOT",
            countLabel = "1 Wallpaper",
            backgroundColors = listOf(Color(0xFFFAF5FF), Color(0xFFF3E8FF)),
            borderColor = Color(0xFFE9D5FF),
            tagBackgroundColor = Color(0xFFF3E8FF),
            tagBorderColor = Color(0xFFD8B4FE),
            tagTextColor = Color(0xFF6B21A8),
            iconBackgroundColor = Color(0xFF8B5CF6),
            titleColor = Color(0xFF581C87),
            subtitleColor = Color(0xFF7E22CE),
            countColor = Color(0xFF7C3AED),
            icon = Icons.Default.AutoAwesome
        ),
        CategoryUiModel(
            category = WallpaperCategory.MY_VIDEOS,
            title = "My Videos",
            subtitle = "Custom & Trimmed Clips",
            tag = "LOCAL",
            countLabel = "Your Gallery",
            backgroundColors = listOf(Color(0xFFF0F9FF), Color(0xFFE0F2FE)),
            borderColor = Color(0xFFBAE6FD),
            tagBackgroundColor = Color(0xFFE0F2FE),
            tagBorderColor = Color(0xFF7DD3FC),
            tagTextColor = Color(0xFF075985),
            iconBackgroundColor = Color(0xFF0EA5E9),
            titleColor = Color(0xFF0C4A6E),
            subtitleColor = Color(0xFF0369A1),
            countColor = Color(0xFF0284C7),
            icon = Icons.Default.VideoLibrary
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        categories.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        CategoryVisualCard(
                            item = item,
                            onClick = { onCategoryClick(item.category) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryVisualCard(
    item: CategoryUiModel,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                1.dp,
                item.borderColor,
                RoundedCornerShape(18.dp)
            )
            .clickable(onClick = onClick)
            .testTag("category_card_${item.category.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(item.backgroundColors))
        ) {
            // Top row with tag and icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = item.tagBackgroundColor,
                    border = BorderStroke(
                        0.5.dp,
                        item.tagBorderColor
                    )
                ) {
                    Text(
                        text = item.tag,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = item.tagTextColor,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(item.iconBackgroundColor),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = "${item.title} Icon",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Bottom title and subtitle
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = item.titleColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        lineHeight = 13.sp
                    ),
                    color = item.subtitleColor,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = item.countLabel,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    ),
                    color = item.countColor
                )
            }
        }
    }
}
