package com.example.devandart.ui.component.ItemCard

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme

interface CommentData {
    val id: String
    val userId: String
    val username: String
    val thumb: String
    val comment: String
    val stampId: String?
    val commentDate: String
    val commentUserId: String
    val hasReplies: String?
}

@Composable
fun <T: CommentData> ItemCardComment(
    modifier: Modifier = Modifier,
    commentData: T
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .setHeader("Referer", "http://www.pixiv.net/")
                .data(commentData?.thumb)
                .crossfade(false)
                .build(),
            contentDescription = commentData?.comment ?: "",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loved),
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape),
        )
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = commentData?.username ?: ""
            )
            if (commentData.stampId?.isNullOrBlank() == false) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context = LocalContext.current)
                        .setHeader("Referer", "http://www.pixiv.net/")
                        .data("https://s.pximg.net/common/images/stamp/generated-stamps/${commentData.stampId}_s.jpg?20180605")
                        .crossfade(false)
                        .build(),
                    contentDescription = commentData.comment,
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.loved),
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                )
            }
            Text(
                text = commentData?.comment ?: "",
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = commentData?.commentDate ?: "",
                fontSize = 9.sp,
            )
        }
    }
}

@Preview
@Composable
fun ItemCardCommentPreview() {
    DevAndArtTheme {
//        ItemCardComment(commentData = null)
    }
}