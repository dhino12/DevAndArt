package com.example.devandart.ui.screen.login

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devandart.R
import com.example.devandart.ui.theme.DevAndArtTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val activity = (LocalContext.current as Activity)
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = color)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.hi___bronya_1),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.Gray, blendMode = BlendMode.Multiply),
                alignment = Alignment.TopCenter
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .size(150.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo"
            )
            Button(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .fillMaxWidth(),
                onClick = {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("URL_DATA", "https://accounts.pixiv.net/login?return_to=https://www.pixiv.net/en/")
                    activity.startActivity(intent)
                    activity.finish()
                }
            ) {
                Text(
                    text = "Login",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("URL_DATA", "https://accounts.pixiv.net/signup?return_to=https%3A%2F%2Fwww.pixiv.net%2Fen%2F&lang=en&source=pc&view_type=page&ref=wwwtop_accounts_index")
                    activity.startActivity(intent)
                    activity.finish()
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = BorderStroke(0.dp, color = Color.Transparent)
            ) {
                Text(
                    text = "Don't have an Account",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    DevAndArtTheme {
        LoginScreen()
    }
}