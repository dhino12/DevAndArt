package com.example.devandart.ui.component.TopAppBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devandart.ui.component.appdrawer.DrawerIcon
import com.example.devandart.ui.screen.home.AppActionBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenTopBar(
    modifier:Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    appActionBar: List<AppActionBar> = listOf(),
    contentTitle: @Composable (modifier: Modifier) -> Unit = {}
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            contentTitle(modifier)
        },
        actions = {
            appActionBar?.let {
                for (appBarAction in it) {
                    Column(
                        modifier = Modifier
                            .clickable { appBarAction.onClick() }
                            .padding(horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = painterResource(id = appBarAction.icon),
                            contentDescription = stringResource(id = appBarAction.description)
                        )
                        Text(
                            text = stringResource(id = appBarAction.description),
                            fontSize = 10.sp
                        )
                    }
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