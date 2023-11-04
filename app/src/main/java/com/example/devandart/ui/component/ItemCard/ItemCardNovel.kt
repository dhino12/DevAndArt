package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun ItemCardNovel(
    modifier: Modifier = Modifier,
    imageIllustration: String? = null,
    onFavorite: () -> Unit = {},
    onDeleteFavorite: () -> Unit = {},
    isFavorite: Boolean = false,
    content: @Composable () -> Unit = {}
) {
    var bookmark by remember { mutableStateOf(isFavorite) }

    Box(modifier = modifier.fillMaxWidth()) {
        Row {
            Column (horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .setHeader("Referer", "http://www.pixiv.net/")
                        .data(imageIllustration)
                        .crossfade(false)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loved),
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .padding(end = 10.dp, bottom = 6.dp)
                        .clip(RoundedCornerShape(4.dp)),
                )
                Row (
                    modifier = Modifier.width(50.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "favorite",
                        tint = Color.Gray
                    )
                    Text(
                        text = "1211",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                    )
                }
            }
            content()
        }
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
}

@Preview
@Composable
fun ItemCardNovelPreview() {
    DevAndArtTheme {
        ItemCardNovel()
    }
}