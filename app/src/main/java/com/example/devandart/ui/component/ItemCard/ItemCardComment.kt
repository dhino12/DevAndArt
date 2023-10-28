package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun ItemCardComment(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.hi___kiana),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {
            Text(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = "Name Author"
            )
            Text(
                text = "comments text",
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = "2023-10-04 00:08",
                fontSize = 9.sp,
            )
        }
    }
}

@Preview
@Composable
fun ItemCardCommentPreview() {
    DevAndArtTheme {
        ItemCardComment()
    }
}