package com.example.devandart.ui.screen.home.Fixiv.manga

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemCardManga
import com.example.devandart.utils.gridItems

@Composable
fun MangaScreen() {
    LazyColumn {
        item {
            TextButton( onClick = { /*TODO*/ } ) {
                Text(text = "Rankings")
            }
            LazyRow(
                userScrollEnabled = true
            ) {
                items(4) {
                    ItemCardIllustration(
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 4.dp, start = 3.dp)
                    )
                }
            }
        }
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Recommended")
            }
        }
        gridItems(
            modifier = Modifier.padding(horizontal = 4.dp),
            count = 4,
            nColumns = 2,
        ) {
            ItemCardManga(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
            )
        }
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Newest")
            }
        }
        gridItems(
            modifier = Modifier.padding(horizontal = 4.dp),
            count = 4,
            nColumns = 2,
        ) {
            ItemCardManga(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
            )
        }
    }
}