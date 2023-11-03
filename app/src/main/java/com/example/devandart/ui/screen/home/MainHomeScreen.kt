package com.example.devandart.ui.screen.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devandart.R
import com.example.devandart.ui.component.TopAppBar.HomeScreenTopBar
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
    navigateToAnotherScreen: (String) -> Unit, // for navigate to searchScreen / newestScreen
    navigateToDetail: (String) -> Unit, // for navigate to detailScreen
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val tabData = getTabList()
    val pagerState = rememberPagerState(pageCount = { tabData.size })

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContent(
                navigateToAnotherScreen = navigateToAnotherScreen,
                scrollBehavior = scrollBehavior,
                drawerState = drawerState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarContent(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToAnotherScreen: (String) -> Unit, // for navigate to searchScreen / newestScreen
) {
    HomeScreenTopBar(
        scrollBehavior = scrollBehavior,
        drawerState = drawerState,
        appActionBar = listOf(
            AppActionBar(
                icon = R.drawable.newest_icon,
                description = R.string.newest_page,
                onClick = { navigateToAnotherScreen("newest") }
            ),
            AppActionBar(
                icon = R.drawable.baseline_search_24,
                description = R.string.search_page,
                onClick = { navigateToAnotherScreen("search") }
            )
        )
    ) {
        Text(
            text = stringResource(id = R.string.home_page),
            style = MaterialTheme.typography.headlineSmall,
        )
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
@Preview(
    device = Devices.PIXEL_2,
    showSystemUi = true,
)
fun HomeScreenPreview() {
    DevAndArtTheme {
        HomeScreen(
            navigateToAnotherScreen = {},
            navigateToDetail = {}
        )
    }
}