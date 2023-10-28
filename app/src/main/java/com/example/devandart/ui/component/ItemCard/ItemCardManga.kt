package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun ItemCardManga(modifier: Modifier = Modifier) {
    var bookmark by remember { mutableStateOf(false) }

    Card (
        modifier = modifier.padding(bottom = 12.dp),
        shape= RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box {
                Image(
                    modifier = Modifier
                        .width(185.dp)
                        .height(222.dp),
                    painter = painterResource(id = R.drawable.bochi),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                )
                IconButton(
                    onClick = { bookmark = !bookmark },
                    modifier = Modifier
                        .background(Color.Transparent)
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(if (bookmark) R.drawable.loved else R.drawable.love),
                        contentDescription = null
                    )
                }
            }
            Text(
                modifier = modifier.padding(top = 5.dp, start = 8.dp),
                text = "Title",
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                modifier = modifier.padding(top = 3.dp, start = 8.dp),
                text = "Description Short",
                maxLines = 1,
                fontSize = 10.sp,
                color = Color.Gray
            )
            Row (
                modifier = modifier.padding(start = 8.dp, top = 3.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = modifier
                        .size(12.dp),
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "favorite",
                    tint = Color.Gray
                )
                Text(
                    modifier = modifier,
                    text = "1211",
                    fontSize = 9.5.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemCardMange() {
    DevAndArtTheme {
        ItemCardManga()
    }
}