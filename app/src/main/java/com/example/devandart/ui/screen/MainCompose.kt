package com.example.devandart.ui.screen


import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.devandart.R
import com.example.devandart.ui.component.alert.AlertDialog
import com.example.devandart.ui.component.appdrawer.AppDrawerContent
import com.example.devandart.ui.navigation.DrawerParams
import com.example.devandart.ui.navigation.Screen
//import com.example.devandart.ui.navigation.mainGraph
import com.example.devandart.ui.screen.detail.DetailScreen
import com.example.devandart.ui.screen.favorite.FavoriteScreen
import com.example.devandart.ui.screen.home.HomeScreen
import com.example.devandart.ui.screen.logout.LogoutScreen
import com.example.devandart.ui.screen.newest.NewestScreen
import com.example.devandart.ui.screen.search.SearchContentScreen
import com.example.devandart.ui.screen.search.SearchScreen
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.MetaGlobalData

@Composable
fun MainCompose (
    modifier: Modifier = Modifier,
    metaGlobalData: MetaGlobalData? = null,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    Surface {
        ModalNavigationDrawer(
            drawerState= drawerState,
            drawerContent = {
                AppDrawerContent(
                    metaGlobalData = metaGlobalData,
                    drawerState = drawerState,
                    menuItems = DrawerParams.drawerButtons,
                    defaultPick = Screen.Home.route,
                ) { onUserPickedOption ->
                    when(onUserPickedOption) {
                        Screen.Home.route -> {
                            navController.popBackStack()

                            navController.navigate(onUserPickedOption) {
                                popUpTo(Screen.Home.route)
                            }
                        }
                        Screen.Newest.route -> {
                            navController.popBackStack()
                            navController.navigate(onUserPickedOption) {
                                popUpTo(Screen.Newest.route)
                            }
                        }
                        Screen.Splash.route -> {
                            navController.navigate(onUserPickedOption) {
                                popUpTo(Screen.Search.route)
                            }
                        }
                        Screen.Favorite.route -> {
                            navController.navigate(onUserPickedOption) {
                                popUpTo(Screen.Favorite.route) {
                                    inclusive = true
                                }
                            }
                        }
                        Screen.Logout.route -> {
                            navController.navigate(onUserPickedOption) {
                                popUpTo(Screen.Logout.route) {
                                    inclusive = true
                                }
                            }
                        }
                        else -> {
                            Log.e("Navigate notfound", onUserPickedOption)
                        }
                    }
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
            ) {
                composable(Screen.Home.route) {
                    HomeScreen(
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
                composable(route = Screen.Search.route) {
                    SearchScreen(
                        drawerState = drawerState,
                        navigateToAnotherScreen = {toScreen ->
                            when(toScreen) {
                                "search" -> navController.navigate(Screen.Search.route)
                                "newest" -> navController.navigate(Screen.Newest.route)
                            }
                        },
                        navigateToContentSearch = { keyword ->
                            navController.navigate(Screen.SearchContentDetail.createRoute(keyword))
                        }
                    )
                }
                composable(
                    route = Screen.SearchContentDetail.route,
                    arguments = listOf(
                        navArgument("keyword") { type = NavType.StringType }
                    )
                ) {
                    val keyword = it.arguments?.getString("keyword") ?: ""
                    SearchContentScreen(
                        drawerState = drawerState,
                        keyword = keyword,
                        navigateToDetail = { artworkId ->
                            navController.navigate(Screen.DetailArt.createRoute(artworkId))
                        },
                        navigateToContentSearch = { keyword ->
                            navController.navigate(Screen.SearchContentDetail.createRoute(keyword))
                            navController.navigateUp()
                        },
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
                        modifier = modifier,
                        navigateBack = { navController.navigateUp() },
                        navigateToDetail = { artworkId ->
                            navController.navigate(Screen.DetailArt.createRoute(artworkId))
                        }
                    )
                }
                composable(route = Screen.Favorite.route) {
                    FavoriteScreen(
                        drawerState = drawerState,
                        navigateToDetail = { artworkId ->
                            navController.navigate(Screen.DetailArt.createRoute(artworkId))
                        },
                        navigateBack = {
                            navController.navigateUp()
                        },
                        metaGlobalData = metaGlobalData
                    )
                }
                composable(route = Screen.Logout.route) {
                    if (metaGlobalData != null) {
                        LogoutScreen(
                            metaGlobalData = metaGlobalData,
                            navigateUp = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(
    device = Devices.PIXEL_2,
    showSystemUi = true,
)
fun HomeScreenPreview() {
    DevAndArtTheme {
        MainCompose()
    }
}