package com.example.devandart.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.devandart.ui.screen.detail.DetailScreen
import com.example.devandart.ui.screen.home.HomeScreen
import com.example.devandart.ui.screen.newest.NewestScreen

@Composable
fun NavigationHost(
    navController: NavHostController,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    cookie: String = ""
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                drawerState = drawerState,
                cookie = cookie,
                navigateToAnotherScreen = {},
                navigateToDetail = { artworkId ->
                    navController.navigate(Screen.DetailArt.createRoute(artworkId))
                }
            )
        }
        composable(route = Screen.Newest.route) {
            NewestScreen(
                drawerState = drawerState,
                navigateToAnotherScreen = {toScreen ->
                    when(toScreen) {
                        "search" -> navController.navigate(Screen.Search.route)
                        "newest" -> navController.navigate(Screen.Newest.route)
                    }
                },
                navigateToDetail = { artworkId ->
                    navController.navigate(Screen.DetailArt.createRoute(artworkId))
                }
            )
        }
        composable(
            route = Screen.DetailArt.route,
            arguments = listOf(
                navArgument("artworkId") { type = NavType.StringType }
            ),
        ) {
            val id = it.arguments?.getString("artworkId") ?: ""
            DetailScreen(
                id = id,
                modifier = Modifier,
                navigateToDetail = { artworkId ->
                    navController.navigate(Screen.DetailArt.createRoute(artworkId))
                }
            )
        }
    }
}