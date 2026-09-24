package com.example.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Foundation delegate pointing to the real Home Screen.
 */
@Composable
fun HomeScreenPlaceholder(
    onOpenSettings: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    HomeScreen(onOpenSettings = onOpenSettings, modifier = modifier)
}
