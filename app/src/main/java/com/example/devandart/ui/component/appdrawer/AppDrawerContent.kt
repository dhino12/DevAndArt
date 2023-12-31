package com.example.devandart.ui.component.appdrawer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.devandart.R
import com.example.devandart.utils.MetaGlobalData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: Enum<T>> AppDrawerContent(
    metaGlobalData: MetaGlobalData? = null,
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo>,
    defaultPick: String,
    onClick: (String) -> Unit
) {
    var currentPick by remember { mutableStateOf(defaultPick) }
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                DrawerItemProfile(
                    modifier = Modifier.padding(vertical = 35.dp, horizontal = 8.dp),
                    metaGlobalData = metaGlobalData
                )
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(menuItems) {item ->
                        if (item.title == R.string.logout_page) return@items
                        AppDrawerItem(item = item) { navOption ->
                            if (currentPick == navOption) {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                                return@AppDrawerItem
                            }

                            currentPick = navOption
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            onClick(navOption)
                        }
                    }
                }
                /**
                 * Logout Nav
                 */
                Box (modifier = Modifier.padding(horizontal = 8.dp)) {
                    AppDrawerItem(item = menuItems.last()) { navOption ->
                        if (currentPick == navOption) {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            return@AppDrawerItem
                        }

                        currentPick = navOption
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        onClick(navOption)
                    }
                }
            }
        }
    }
}