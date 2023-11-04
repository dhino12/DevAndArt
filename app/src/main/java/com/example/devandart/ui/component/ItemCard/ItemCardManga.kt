package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun ItemCardManga(
    modifier: Modifier = Modifier,
    imageIllustration: String? = null,
    title: String? = null,
    shortDescription: String? = null,
    favoriteCount: String? = null,
    onFavorite: () -> Unit = {},
    onDeleteFavorite: () -> Unit = {},
    isFavorite: Boolean = false,
) {
    var bookmark by remember { mutableStateOf(isFavorite) }

    Card (
        modifier = modifier.padding(bottom = 12.dp),
        shape= RoundedCornerShape(3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .setHeader("Referer", "http://www.pixiv.net/")
                        .data(imageIllustration)
                        .crossfade(false)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loved),
                    modifier = Modifier
                        .width(185.dp)
                        .height(222.dp),
                )
                IconButton(
                    onClick = {
                        if (bookmark) {
                            bookmark = false;
                            onDeleteFavorite()
                        } else {
                            bookmark = true;
                            onFavorite()
                        }
                    },
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
                text = title ?: "Title",
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                modifier = modifier.padding(top = 3.dp, start = 8.dp).fillMaxWidth(),
                text = shortDescription ?: "Description Shortaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                maxLines = 1,
                fontSize = 10.sp,
                color = Color.Gray,
                overflow = TextOverflow.Ellipsis
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
                    text = favoriteCount ?: "1211",
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