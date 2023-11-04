package com.example.devandart.ui.component.customButton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devandart.ui.theme.DevAndArtTheme

@Composable
fun FlatButton(
    modifier: Modifier = Modifier,
    selected: Boolean = false,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Box {
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
            ),
            contentPadding = PaddingValues(horizontal = 8.dp),
            modifier = modifier
                .padding(start = 5.dp, end = 5.dp, top = 8.dp, bottom = 4.dp)
        ) {
            content()
        }
    }
}

@Preview
@Composable
fun PreviewFlatButton() {
    DevAndArtTheme {
        FlatButton(onClick = { }) {

        }
    }
}