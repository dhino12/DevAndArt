package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme

interface ProfileData {
    val name: String?
    val username: String?
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
            Image(
                modifier = modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.hi___kiana),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop
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
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "TITLE",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.White,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.hi___kiana),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = modifier.padding(horizontal = 3.dp),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                text = "titladadadwaewrwe"
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