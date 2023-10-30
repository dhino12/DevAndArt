package com.example.devandart.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Splash: Screen("splash")
    object Newest: Screen("newest")
    object DetailArt: Screen("home/{artworkId}") {
        fun createRoute(artworkId: String) = "home/$artworkId"
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen
)