package com.example.devandart.ui.screen.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.TopAppBar.HomeScreenTopBar
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.AppActionBar
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.ui.screen.home.Fixiv.illustrations.LoadingScreen
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.gridItems

data class TagSimple(
    val id: String? = null,
    val tag: String? = null,
    val url: String? = null,
    val romaji: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen (
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToAnotherScreen: (String) -> Unit, // for navigate to searchScreen / newestScreen
    navigateToContentSearch: (String) -> Unit, // for navigate to detailScreen
    viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var loading = true
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//    var searchValue by remember {mutableStateOf("")}

    var recommendedTag : MutableList<TagSimple>? = mutableListOf()
    var dailyRank: MutableList<ResultItemIllustration>? = mutableListOf()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                LoadingScreen(loading = loading)
                viewModel.getAllTagRecommend()
            }
            is UiState.Success -> {
                uiState.data.thumbnails?.forEach { thumbnail ->
                    uiState.data.recommendTags?.illust?.find {
                        if (thumbnail.id == it.ids?.get(0)) {
                            recommendedTag?.add(
                                TagSimple(
                                    id = it.ids?.get(0),
                                    tag = it.tag,
                                    url = thumbnail.url,
                                    romaji = uiState.data.tagTranslation?.get(it.tag)?.romaji ?: "",
                                )
                            )
                            true
                        }
                        false
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("recommended", uiState.errorMessage)
            }
        }
    }
    if (recommendedTag?.size == 0) return
    var randomTag = recommendedTag?.random()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContent(
                navigateToAnotherScreen = navigateToAnotherScreen,
                scrollBehavior = scrollBehavior,
                drawerState = drawerState,
                searchValueState = viewModel.uiSearchValue,
                onValueSearchChange = { viewModel.updateSearchValueText(it) },
                onKeyboardDone = { navigateToContentSearch(viewModel.uiSearchValue) }
            )
        }
    ) {
        LazyColumn (modifier = Modifier.padding(paddingValues = it)) {
            item {
                Box (
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .clickable { navigateToContentSearch(randomTag?.tag ?: "") }
                ) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context = LocalContext.current)
                            .setHeader("Referer", "http://www.pixiv.net/")
                            .data(randomTag?.url)
                            .crossfade(false)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter,
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.loved),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp),
                    )
                    Spacer(
                        modifier = Modifier
                            .alpha(0.4F)
                            .background(Color.Black)
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 28.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "#${randomTag?.tag}" ?: "#Title",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = randomTag?.romaji ?: "romaji",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            gridItems(
                modifier = Modifier
                    .height(150.dp),
                count = recommendedTag?.size ?: 0,
                nColumns = 3,
                horizontalArrangement = Arrangement.Center
            ) {
                Box (modifier = Modifier.clickable {
                    navigateToContentSearch(recommendedTag?.get(it)?.tag ?: "")
                    viewModel.updateSearchValueText(recommendedTag?.get(it)?.tag ?: "")
                }) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context = LocalContext.current)
                            .setHeader("Referer", "http://www.pixiv.net/")
                            .data(recommendedTag?.get(it)?.url)
                            .crossfade(false)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.TopCenter,
                        error = painterResource(id = R.drawable.ic_broken_image),
                        placeholder = painterResource(id = R.drawable.loved),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(bottom = 2.dp, end = 2.dp, top = 2.dp),
                    )
                    Spacer(
                        modifier = Modifier
                            .alpha(0.4F)
                            .background(Color.Black)
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 28.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = "#${recommendedTag?.get(it)?.tag}" ?: "#Title",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = recommendedTag?.get(it)?.romaji ?: "romaji",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarContent(
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToAnotherScreen: (String) -> Unit, // for navigate to searchScreen / newestScreen
    searchValueState:String = "",
    onValueSearchChange: (String) -> Unit = {},
    onKeyboardDone: () -> Unit = {},
) {
    HomeScreenTopBar(
        scrollBehavior = scrollBehavior,
        drawerState = drawerState,
        appActionBar = listOf(
            AppActionBar(
                icon = R.drawable.newest_icon,
                description = R.string.newest_page,
                onClick = { navigateToAnotherScreen("newest") }
            ),
            AppActionBar(
                icon = R.drawable.baseline_search_24,
                description = R.string.search_page,
                onClick = { navigateToAnotherScreen("search") }
            )
        )
    ) {
        TextField(
            modifier = Modifier,
            textStyle = TextStyle.Default.copy(fontSize = 12.sp),
            value = searchValueState,
            onValueChange = onValueSearchChange,
            placeholder = {
                Text(text = "search any idea", fontSize = 12.sp)
            },
            maxLines = 1,
           keyboardOptions = KeyboardOptions.Default.copy(
               imeAction = ImeAction.Done
           ),
            keyboardActions = KeyboardActions(
                onDone = { onKeyboardDone() }
            ),
            singleLine = true,
        )
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    DevAndArtTheme {
        SearchScreen(
            navigateToAnotherScreen = {},
            navigateToContentSearch = {}
        )
    }
}