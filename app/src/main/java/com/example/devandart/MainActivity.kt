package com.example.devandart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.indicators.LoadingScreen
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.HomeActivity
import com.example.devandart.ui.screen.login.LoginActivity
import com.example.devandart.ui.theme.DevAndArtTheme

class MainActivity : ComponentActivity() {
    private var cookie: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModelMain: MainViewModel by viewModels { factory }

        setContent {
            DevAndArtTheme {
                MainScreen(viewModelMain = viewModelMain)
//                MainCompose(cookie = "illustration cookie 1")

            }
        }
    }
}
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModelMain: MainViewModel
) {
    viewModelMain.uiState.collectAsState().value.let { state ->
        when(state) {
            is UiState.Loading -> {
                viewModelMain.getCookie()
                LoadingScreen(loading = true)
            }
            is UiState.Success -> {
                val activity = LocalContext.current as Activity
                val intent = Intent(activity, HomeActivity::class.java)
                intent.putExtra("COOKIE_VALUE", state.data.cookie)
                ViewModelFactory.destroyInstance()
                activity.startActivity(intent)
                activity.finish()
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_SHORT).show()

                val activity = LocalContext.current as Activity
                val intent = Intent(activity, LoginActivity::class.java)
                activity.startActivity(intent)
                activity.finish()
            }
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