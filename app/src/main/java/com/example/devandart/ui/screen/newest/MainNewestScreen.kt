package com.example.devandart.ui.screen.newest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.example.devandart.R
import com.example.devandart.ui.component.TopAppBar.HomeScreenTopBar
import com.example.devandart.ui.screen.home.AppActionBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewestScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToAnotherScreen: (String) -> Unit, // for navigate to searchScreen / newestScreen
    navigateToDetail: (String) -> Unit, // for navigate to detailScreen
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
//            TabLayout(tabData = tabData, pagerState = pagerState)
//            TabContent(
//                navigateToDetail = navigateToDetail ,
//                tabData = tabData,
//                pagerState = pagerState
//            )
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
            text = stringResource(id = R.string.newest_page),
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}
