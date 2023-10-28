package com.example.devandart.ui.screen.home.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.devandart.MainActivity
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.theme.DevAndArtTheme

class WebViewActivity : ComponentActivity() {

    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val viewModelAuth: LoginViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevAndArtTheme {
                // on below line we are specifying background color for our application
                WebViewComponent(
                    url = "https://accounts.pixiv.net/login?return_to=https%3A%2F%2Fwww.pixiv.net%2Fen%2F&lang=en&source=pc&view_type=page",
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
                    Log.d("Cookie get from webView", cookies.toString())
                }
            }
            webView
        }
    )

    cookies?.let { cookieValue ->
        Log.e("cookieContains", cookieValue.contains("PHPSESSID=").toString())
        if (cookieValue.contains("login_ever=yes;")) {
            val activity = (LocalContext.current as Activity)
            viewModel.authCookie(
                cookie = cookieValue,
                userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.46"
            )
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("cookie", cookieValue)
            activity.startActivity(intent)
            activity.finish()
        }
        // Lakukan sesuatu dengan cookieValue di sini
        // Misalnya, cetak ke log atau kirim ke server
        // Jangan lupa untuk menangani situasi jika cookies masih null (belum didapatkan)
    }
}

