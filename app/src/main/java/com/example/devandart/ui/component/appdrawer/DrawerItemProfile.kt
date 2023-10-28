package com.example.devandart.ui.component.appdrawer

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devandart.R
import com.example.devandart.ui.component.customButton.FlatButton
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun DrawerItemProfile(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .width(60.dp)
                .height(60.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.hi___kiana),
            contentDescription = null,
        )
        Column {
            Text(text = "Joko")
            FlatButton(
                modifier = Modifier.clip(RoundedCornerShape(3.dp)).height(height = 40.dp),
                onClick = { /*TODO*/ },
            ) {
                Text(text = "About Premium")
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