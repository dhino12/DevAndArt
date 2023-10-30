package com.example.devandart.ui.component.ItemCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun ItemCardIllustration(
    modifier: Modifier = Modifier,
    imageIllustration: String? = null,
    content: @Composable () -> Unit = {}
) {
    var bookmark by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .width(195.dp)
            .height(184.dp)
    ){
        Card(
            shape= RoundedCornerShape(3.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
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
                    .width(195.dp)
                    .height(184.dp),
            )
//            Image(
//                modifier = Modifier,
//                painter = painterResource(id = R.drawable.hi___bronya_1),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                alignment = Alignment.TopCenter,
//            )
        }
        if (!content.equals({})) {
            content()
//            ItemProfileShorts(
//                modifier = modifier
//                    .align(Alignment.BottomCenter)
//            )
        }
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
}

@Preview
@Composable
fun PreviewItemCardIllustration() {
    DevAndArtTheme {
        ItemCardIllustration(
             imageIllustration = ""
        ) {}
    }
}
