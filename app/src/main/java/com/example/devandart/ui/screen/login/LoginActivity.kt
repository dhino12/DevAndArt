package com.example.devandart.ui.screen.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.theme.DevAndArtTheme

class LoginActivity : ComponentActivity() {
//    private var cookie: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        if (intent.getStringExtra("cookie") != null) {
//            cookie = intent.getStringExtra("cookie") as String
//            // Toast.makeText(this, cookie, Toast.LENGTH_SHORT).show()
//        }

        setContent {
            DevAndArtTheme {
                LoginScreen(
                    color = Color.Transparent
                )
            }
        }
    }
}

