package com.example.devandart

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.devandart.ui.MainCompose
import com.example.devandart.ui.screen.home.login.LoginActivity
import com.example.devandart.ui.theme.DevAndArtTheme

class MainActivity : ComponentActivity() {
    private var cookie: String? = null

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        openChromeForLogin()
        if (intent.getStringExtra("cookie") != null) {
            cookie = intent.getStringExtra("cookie") as String

            // Toast.makeText(this, cookie, Toast.LENGTH_SHORT).show()
        } else {
            val activity = this
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
        setContent {
            DevAndArtTheme {
//                // A surface container using the 'background' color from the theme
                MainCompose()
            }
//
        }
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DevAndArtTheme {
        Greeting("Android")
    }
}