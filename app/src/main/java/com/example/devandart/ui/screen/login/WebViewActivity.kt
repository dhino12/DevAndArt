package com.example.devandart.ui.screen.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.devandart.MainActivity
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.theme.DevAndArtTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModelAuth: LoginViewModel by viewModels { factory }
        val urlData = intent.getStringExtra("URL_DATA")
        setContent {
            DevAndArtTheme {
                // on below line we are specifying background color for our application
                WebViewComponent(
                    url = "https://accounts.pixiv.net/login?return_to=https://www.pixiv.net/en/",
                    viewModel = viewModelAuth,
                )
            }
        }
    }
}

@Composable
fun WebViewComponent(
    url: String,
    viewModel: LoginViewModel,
) {
    var cookies: String? by remember { mutableStateOf(null) } // Deklarasikan variabel untuk menyimpan cookies
    val coroutineScope = rememberCoroutineScope()

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val webView = WebView(context)
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    // Mendapatkan cookies
                    cookies = CookieManager.getInstance().getCookie(url)
                }
            }
            webView
        }
    )

    cookies?.let { cookieValue ->
        Log.d("Cookie  let", cookieValue.toString())

        if (cookieValue.contains("login_ever=yes;")) {
            viewModel.updateItem(ItemCookie(cookie = cookieValue))
            coroutineScope.launch {
                viewModel.saveItem()
            }
            CookieManager.getInstance().removeAllCookies { }

            val activity = (LocalContext.current as Activity)
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("COOKIE", cookieValue)
            ViewModelFactory.destroyInstance()
            activity.startActivity(intent)
            activity.finish()
        }
        // Lakukan sesuatu dengan cookieValue di sini
        // Misalnya, cetak ke log atau kirim ke server
        // Jangan lupa untuk menangani situasi jika cookies masih null (belum didapatkan)
    }
}

