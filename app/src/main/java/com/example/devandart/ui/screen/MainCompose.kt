package com.example.devandart.ui.screen


import android.util.Log
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.devandart.ui.component.appdrawer.AppDrawerContent
import com.example.devandart.ui.navigation.DrawerParams
import com.example.devandart.ui.navigation.Screen
//import com.example.devandart.ui.navigation.mainGraph
import com.example.devandart.ui.screen.detail.DetailScreen
import com.example.devandart.ui.screen.home.HomeScreen
import com.example.devandart.ui.screen.newest.NewestScreen
import com.example.devandart.ui.theme.DevAndArtTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainCompose (
    modifier: Modifier = Modifier,
    cookie: String = "",
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    Surface {
        ModalNavigationDrawer(
            drawerState= drawerState,
            drawerContent = {
                AppDrawerContent(
                    drawerState = drawerState,
                    menuItems = DrawerParams.drawerButtons,
                    defaultPick = Screen.Home.route,
                ) { onUserPickedOption ->
                    when(onUserPickedOption) {
                        Screen.Home.route -> {
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
                        cookie = cookie,
                        navigateToDetail = { artworkId ->
                            navController.navigate(Screen.DetailArt.createRoute(artworkId))
                        }
                    )
                }
                composable(route = Screen.Newest.route) {
                    NewestScreen(
                        drawerState = drawerState,
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
                        modifier = modifier,
                        navigateToDetail = { artworkId ->
                            navController.navigate(Screen.DetailArt.createRoute(artworkId))
                        }
                    )
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