package com.example.devandart.ui.screen.search

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.TopAppBar.HomeScreenTopBar
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.AppActionBar
import com.example.devandart.ui.screen.home.Fixiv.illustrations.LoadingScreen
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.gridItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContentScreen(
    keyword: String,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    navigateToAnotherScreen: (String) -> Unit, // for navigate to detailScreen
    navigateToDetail: (String) -> Unit,
    viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var loading = true
    var illustration : List<ResultItemIllustration>? = null
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.uiStateSearchKeyword.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                LoadingScreen(loading = loading)
                viewModel.getSearchByKeyword(keyword)
            }
            is UiState.Success -> {
                illustration = uiState.data.illustManga.data
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("recommended", uiState.errorMessage)
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarContent(
                navigateToAnotherScreen = navigateToAnotherScreen,
                scrollBehavior = scrollBehavior,
                drawerState = drawerState,
                searchValueState = viewModel.uiSearchValue,
                onValueSearchChange = { viewModel.updateSearchValueText(it) },
                onKeyboardDone = { viewModel.getSearchByKeyword(viewModel.uiSearchValue) }
            )
        }
    ) {
        IllustrationContent(
            contentPaddingValues = it,
            illustrations = illustration ?: listOf(),
            navigateToDetail = navigateToDetail,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IllustrationContent(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    illustrations: List<ResultItemIllustration>,
    navigateToDetail: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = contentPaddingValues
    ) {
        items(illustrations) {illustration ->
            ItemCardIllustration(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
                    .clickable { navigateToDetail(illustration.id ?: "") },
                imageIllustration = illustration.url ?: ""
            )
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


@Composable
@Preview
fun IllustrationContentPreview() {
    DevAndArtTheme {
        IllustrationContent(
            illustrations = listOf(),
            navigateToDetail = {},
            contentPaddingValues = PaddingValues(12.dp)
        )
    }
}