package com.example.devandart.ui.screen.home.Fixiv.novel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.devandart.R
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemCardNovel

@Composable
fun NovelScreen() {
    LazyColumn {
        item {
            TextButton(onClick = {}) {
                Image(
                    modifier = Modifier.width(35.dp),
                    painter = painterResource(R.drawable.crown),
                    contentDescription = "ranking"
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "Rankings",
                )
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
                Image(
                    modifier = Modifier.width(20.dp),
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Recommended"
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "Recommended"
                )
            }
        }
        items(8) {
            ItemCardNovel(modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp))
        }
    }
}