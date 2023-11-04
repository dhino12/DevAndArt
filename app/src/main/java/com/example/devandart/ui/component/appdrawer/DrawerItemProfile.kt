package com.example.devandart.ui.component.appdrawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.MetaGlobalData

@Composable
fun DrawerItemProfile(
    modifier: Modifier = Modifier,
    metaGlobalData: MetaGlobalData? = null
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .setHeader("Referer", "http://www.pixiv.net/")
                .data(metaGlobalData?.userData?.profileImg)
                .crossfade(false)
                .build(),
            contentDescription = metaGlobalData?.userData?.name,
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loved),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(60.dp)
                .height(60.dp)
                .clip(CircleShape),
        )
        Column {
            Text(
                text = metaGlobalData?.userData?.name ?: "Joko",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.padding(vertical = 3.dp))
            OutlinedButton(
                modifier = Modifier
                    .height(height = 40.dp)
                    .padding(vertical = 3.dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "About Premium"
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawerItemProfilePreview() {
    DevAndArtTheme {
        DrawerItemProfile()
    }
}