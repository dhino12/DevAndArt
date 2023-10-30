package com.example.devandart.ui.screen.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.devandart.R
import com.example.devandart.ui.component.appdrawer.DrawerIcon
import com.example.devandart.ui.component.tabLayout.TabContent
import com.example.devandart.ui.component.tabLayout.TabLayout
import com.example.devandart.ui.theme.DevAndArtTheme

@OptIn(ExperimentalMaterial3Api::class,ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    cookie: String = "",
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToDetail: (String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val tabData = getTabList()
    val pagerState = rememberPagerState(pageCount = { tabData.size })

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeScreenTopBar(
                scrollBehavior = scrollBehavior,
                drawerState = drawerState,
                appActionBar = listOf(
                    AppActionBar(
                        icon = R.drawable.loved,
                        description = R.string.home_page,
                        onClick = {  }
                    )
                )
            )
        }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TabLayout(tabData = tabData, pagerState = pagerState)
            TabContent(
                cookie = cookie,
                navigateToDetail = navigateToDetail ,
                tabData = tabData,
                pagerState = pagerState
            )
        }
    }
}

data class AppActionBar(
    @DrawableRes val icon: Int,
    @StringRes val description: Int,
    val onClick: () -> Unit
)

private fun getTabList(): List<Pair<String, ImageVector>> {
    return listOf(
        "Fixiv" to Icons.Default.Home,
        "Deviations" to Icons.Default.Search,
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    modifier:Modifier = Modifier,
    drawerState: DrawerState? = null,
    scrollBehavior: TopAppBarScrollBehavior,
    appActionBar: List<AppActionBar>? = null,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineSmall,
            )
        },
        actions = {
            appActionBar?.let {
                for (appBarAction in it) {
                    Icon(
                        painter = painterResource(id = appBarAction.icon),
                        contentDescription = stringResource(id = appBarAction.description)
                    )
                }
            }
        },
        navigationIcon = {
            if (drawerState != null) {
                DrawerIcon(drawerState = drawerState)
            }
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(
    device = Devices.PIXEL_2,
    showSystemUi = true,
)
fun HomeScreenPreview() {
    DevAndArtTheme {
        HomeScreen(navigateToDetail = {})
    }
}