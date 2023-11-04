package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

interface ProfileData {
    val name: String?
    val username: String?
    val imageUser: String?
}

@Composable
fun <T: ProfileData> ItemProfile(
    dataProfile: T,
    modifier: Modifier = Modifier,
    showUsername: Boolean = true,
    contentSupport: @Composable () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .setHeader("Referer", "http://www.pixiv.net/")
                    .data(dataProfile.imageUser)
                    .crossfade(false)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loved),
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape),
            )
            Column (
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                Text(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    text = dataProfile.name ?: "Name Author"
                )
                if (showUsername) {
                    Text(
                        text = dataProfile.username ?: "username",
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }
            }
        }
        contentSupport()
    }
}

@Composable
fun ItemProfileShorts(
    modifier: Modifier = Modifier,
    title: String? = null,
    username: String? = null,
    image: String? = null
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            text = title ?: "Petualangan Joko",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .setHeader("Referer", "http://www.pixiv.net/")
                    .data(image)
                    .crossfade(false)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loved),
                modifier = modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(CircleShape),
            )
            Text(
                modifier = modifier.padding(horizontal = 3.dp),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = username ?: "Joko"
            )
        }
    }
}

//
//@Preview
//@Composable
//fun ItemProfilePreview() {
//    DevAndArtTheme {
//        ItemProfile(
//            dataProfile = ProfileData,
//            contentSupport = {}
//        )
//    }
//}

@Preview
@Composable
fun ItemProfileShortsPreview() {
    DevAndArtTheme {
        ItemProfileShorts()
    }
}