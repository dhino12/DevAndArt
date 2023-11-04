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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.indicators.LoadingScreen
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.HomeActivity
import com.example.devandart.ui.screen.login.ItemCookie
import com.example.devandart.ui.screen.login.LoginActivity
import com.example.devandart.ui.screen.login.UserItem
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.MetaGlobalData
import com.example.devandart.utils.UserData
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
    var metaDataGlobal by remember { mutableStateOf(MetaGlobalData(
        id = 1,
        cookie = "",
        userData = null,
        token = "",
    )) }
    /**
     * Fetch to DB
     */
    viewModelMain.uiState.collectAsState().value.let { state ->
        when(state) {
            is UiState.Loading -> {
                viewModelMain.getCookie()
                LoadingScreen(loading = true)
            }
            is UiState.Success -> {

                if (state.data.cookie.isNotBlank() && state.data.csrf_token.isBlank()) {
                    /**
                     * Fetch to API
                     */
                    viewModelMain.uiStateHtml.collectAsState().value.let { uiStateHtml ->
                        when(uiStateHtml) {
                            is UiState.Loading -> { viewModelMain.getHTML() }
                            is UiState.Success -> {
                                val doc = Jsoup.parse(uiStateHtml.data.string())
                                val metaElement = doc.select("meta[name=global-data]")
                                val content = metaElement.attr("content")
                                if (content.isNotBlank()) {
                                    metaDataGlobal = toJsonMetaData(content)
                                    metaDataGlobal.cookie = state.data.cookie
                                    metaDataGlobal.id = state.data.id
                                    Log.e("contentJSON", metaDataGlobal.toString())

                                    viewModelMain.updateCookieDb(ItemCookie(
                                        id = state.data.id,
                                        cookie = state.data.cookie,
                                        tokenCsrf = metaDataGlobal.token
                                    ))
                                    viewModelMain.saveUserDb(UserItem(
                                        id = metaDataGlobal.userData?.id ?: "",
                                        pixivId = metaDataGlobal.userData?.pixivId ?: "",
                                        name = metaDataGlobal.userData?.name ?: "",
                                        profileImg = metaDataGlobal.userData?.profileImg ?: "",
                                        profileImgBig = metaDataGlobal.userData?.profileImgBig ?: "",
                                        premium = metaDataGlobal.userData?.premium ?: false,
                                        adult = metaDataGlobal.userData?.adult ?: false
                                    ))
                                    moveToHomeActivity(
                                        LocalContext.current as Activity,
                                        metaDataGlobal = metaDataGlobal,
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
                } else {
                    // end if
                    viewModelMain.uiStateUser.collectAsState().value.let {userState ->
                        when(userState) {
                            is UiState.Loading -> {
                                viewModelMain.getUser()
                            }
                            is UiState.Success -> {
                                metaDataGlobal = MetaGlobalData(
                                    id = state.data.id,
                                    token = state.data.csrf_token,
                                    cookie = state.data.cookie,
                                    userData = UserData(
                                        id = userState.data.id,
                                        pixivId= userState.data.pixivId,
                                        name = userState.data.name,
                                        profileImg = userState.data.profileImg,
                                        profileImgBig = userState.data.profileImgBig,
                                        premium = userState.data.premium,
                                        adult = userState.data.adult,
                                    ),
                                )

                                moveToHomeActivity(
                                    LocalContext.current as Activity,
                                    metaDataGlobal = metaDataGlobal,
                                )
                            }
                            is UiState.Error -> {
                                Toast.makeText(LocalContext.current as Activity, "Error getUser", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, "You're unauthorized please login / register", Toast.LENGTH_SHORT).show()

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