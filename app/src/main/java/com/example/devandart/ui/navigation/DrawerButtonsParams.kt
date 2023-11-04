package com.example.devandart.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import com.example.devandart.R
import com.example.devandart.ui.component.appdrawer.AppDrawerItemInfo

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
        AppDrawerItemInfo(
            drawerOption = Screen.Favorite,
            title = R.string.favorite_page,
            drawableId = R.drawable.loved,
            descriptionId = R.string.favorite_page,
        ),
        AppDrawerItemInfo(
            drawerOption = Screen.Logout,
            title = R.string.logout_page,
            drawableId = R.drawable.baseline_logout_to_app_24,
            descriptionId = R.string.logout_page,
        )
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