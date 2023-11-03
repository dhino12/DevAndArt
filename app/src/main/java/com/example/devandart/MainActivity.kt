package com.example.devandart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.indicators.LoadingScreen
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.HomeActivity
import com.example.devandart.ui.screen.login.ItemCookie
import com.example.devandart.ui.screen.login.LoginActivity
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.MetaGlobalData
import com.example.devandart.utils.toJsonMetaData
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainActivity : ComponentActivity() {
    private var cookie: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cookie = intent.getStringExtra("COOKIE") ?: ""

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this, cookie)
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
    val coroutineScope = rememberCoroutineScope()

    viewModelMain.uiState.collectAsState().value.let { state ->
        when(state) {
            is UiState.Loading -> {
                viewModelMain.getCookie()
                LoadingScreen(loading = true)
            }
            is UiState.Success -> {
                if (state.data.cookie.isNotBlank() && state.data.csrf_token.isNotBlank()) {
                    val metaGlobalData = MetaGlobalData(
                        token = state.data.csrf_token,
                        cookie = state.data.cookie,
                        userData = null,
                    )
                    moveToHomeActivity(
                        LocalContext.current as Activity,
                        metaDataGlobal = metaGlobalData,
                    )
                    return
                }
                viewModelMain.uiStateHtml.collectAsState().value.let { uiStateHtml ->
                    when(uiStateHtml) {
                        is UiState.Loading -> { viewModelMain.getHTML() }
                        is UiState.Success -> {
                            val doc = Jsoup.parse(uiStateHtml.data.string())
                            val metaElement = doc.select("meta[name=global-data]")
                            val content = metaElement.attr("content")
                            if (content.isNotBlank()) {
                                val metaGlobalData: MetaGlobalData = toJsonMetaData(content)
                                metaGlobalData.cookie = state.data.cookie
                                coroutineScope.launch {
                                    viewModelMain.updateCookieDb(ItemCookie(
                                        id = state.data.id,
                                        cookie = state.data.cookie,
                                        tokenCsrf = metaGlobalData.token
                                    ))
                                }
                                moveToHomeActivity(
                                    LocalContext.current as Activity,
                                    metaDataGlobal = metaGlobalData,
                                )
                            } else {
                                Log.e("contentJSON", content)
                                Log.e("contentJSON", uiStateHtml.data.string())
                            }
                        }
                        is UiState.Error -> {
                            Toast.makeText(LocalContext.current, uiStateHtml.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
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

fun moveToHomeActivity(context: Activity, metaDataGlobal: MetaGlobalData) {
    val intent = Intent(context, HomeActivity::class.java)
    intent.putExtra("METADATA_VALUE", metaDataGlobal)
    ViewModelFactory.destroyInstance()
    context.startActivity(intent)
    context.finish()
}