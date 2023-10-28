package com.example.devandart.ui.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.devandart.R
import com.example.devandart.ui.component.appdrawer.AppDrawerItemInfo
import com.example.devandart.ui.screen.home.HomeScreen

object DrawerParams {
    val drawerButtons = arrayListOf(
        AppDrawerItemInfo(
            drawerOption= Screen.Home,
            title = R.string.home_page,
            drawableId = R.drawable.baseline_home_24,
            descriptionId = R.string.home_page
        ),
        AppDrawerItemInfo(
            drawerOption= Screen.Newest,
            title = R.string.newest_page,
            drawableId = R.drawable.newest_icon,
            descriptionId = R.string.newest_page
        ),
    )
}

//@OptIn(ExperimentalMaterial3Api::class)
//fun NavGraphBuilder.mainGraph(drawerState: DrawerState, navController:NavHostController) {
//    navigation(startDestination = Screen.Home.route, route = Route.Home.route) {
//        composable(Screen.Home.route) {
//            HomeScreen(
//                drawerState = drawerState,
//                navigateToDetail = { artworkId ->
//                    navController.navigate(Screen.DetailArt.createRoute(artworkId))
//                }
//            )
//        }
//    }
//}