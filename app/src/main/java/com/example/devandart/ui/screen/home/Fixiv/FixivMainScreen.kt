package com.example.devandart.ui.screen.home.Fixiv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.ui.component.customButton.FlatButton
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationScreen
import com.example.devandart.ui.screen.home.Fixiv.manga.MangaScreen
import com.example.devandart.ui.screen.home.Fixiv.novel.NovelScreen

@Composable
fun FixivScreen(
    modifier:Modifier = Modifier,
    cookie: String = "",
    titleTopBar: String = "",
    navigateToDetail: (String) -> Unit
) {
    var selectedTab by remember {mutableStateOf(0)}
    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            FlatButton(
                modifier = modifier.size(width = 110.dp, height = 40.dp),
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
            ) {
                Text(text = "Illustration")
            }
            FlatButton(
                modifier = modifier.size(width = 110.dp, height = 40.dp),
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 }
            ) {
                Text(text = "Manga")
            }
            FlatButton(
                modifier = modifier.size(width = 110.dp, height = 40.dp),
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 }
            ) {
                Text(text = "Novel")
            }
        }
        when (selectedTab) {
            0 -> {
                // Tampilkan konten untuk Tab 1
                IllustrationScreen(
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }
            1 -> {
                // Tampilkan konten untuk Tab 2
                MangaScreen(
                    navigateToDetail = navigateToDetail
                )
            }
            2 -> {
                NovelScreen()
            }
            // Tambahkan lebih banyak case sesuai jumlah tab yang Anda miliki
        }

    }
}