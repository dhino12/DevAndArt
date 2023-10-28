package com.example.devandart.ui.screen.newest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.example.devandart.R
import com.example.devandart.ui.component.tabLayout.TabContent
import com.example.devandart.ui.component.tabLayout.TabLayout
import com.example.devandart.ui.screen.home.AppActionBar
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationScreen
import com.example.devandart.ui.screen.home.HomeScreenTopBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewestScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToDetail: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

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
//            TabLayout(tabData = tabData, pagerState = pagerState)
//            TabContent(
//                navigateToDetail = navigateToDetail ,
//                tabData = tabData,
//                pagerState = pagerState
//            )
        }
    }
}