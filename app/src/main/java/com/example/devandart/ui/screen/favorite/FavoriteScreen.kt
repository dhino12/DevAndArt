package com.example.devandart.ui.screen.favorite

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemFavoriteData
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.ItemFavorite
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.MetaGlobalData

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    metaGlobalData: MetaGlobalData? = null,
    navigateToDetail: (String) -> Unit,
    navigateBack: () -> Unit = {},
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    var isFavorite by remember { mutableStateOf(mutableListOf<ResultItemFavorite>()) }
    var favoriteItem: ResultItemFavoriteData by remember { mutableStateOf(ResultItemFavoriteData()) }

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        uiState -> when(uiState) {
            is UiState.Loading -> {
                viewModel.getAllFavorite(metaGlobalData?.userData?.id ?: "")
            }
            is UiState.Success -> {
                favoriteItem = uiState.data
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    viewModel.uiStateFav.collectAsState(initial = UiState.Loading).value.let { uiStateFavorite ->
        when(uiStateFavorite) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                isFavorite.add(uiStateFavorite.data)
                Toast.makeText(LocalContext.current, "Success Favorite", Toast.LENGTH_SHORT).show()
                Log.e("isFavorites", isFavorite.toString())
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, "Error ${uiStateFavorite.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    FavoriteContent(
        favoritesData = favoriteItem,
        navigateBack = navigateBack,
        navigateToDetail = navigateToDetail,
        updateStateFavorite = { viewModel.setFavorite(
            ItemFavorite(
                illustId = it.illustId,
                restrict = it.restrict,
                tags = it.tags,
                comment = it.comment,
            )
        ) },
        deleteFavorite = { idBookmark -> viewModel.deleteFavorite(idBookmark) },
        bookmarks = isFavorite ?: null
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteContent(
    favoritesData: ResultItemFavoriteData,
    navigateToDetail: (String) -> Unit,
    navigateBack: () -> Unit = {},
    updateStateFavorite: (ItemFavorite) -> Unit,
    deleteFavorite: (String) -> Unit,
    bookmarks: List<ResultItemFavorite>? = null,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navigateBack() }
                    )
                },
                title = {
                    Text(text = "Favorite")
                }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            contentPadding = it
        ) {
            items(favoritesData.works) {favoriteWork ->
                ItemCardIllustration(
                    modifier = Modifier
                        .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
                        .clickable {
                            navigateToDetail(favoriteWork.id ?: "")
                        },
                    imageIllustration = favoriteWork.url,
                    onFavorite = {updateStateFavorite(ItemFavorite(
                        illustId = favoriteWork.id,
                    ))},
                    onDeleteFavorite = { deleteFavorite(
                        favoriteWork.bookmarkData?.id ?:
                        bookmarks?.find { it.illustId == favoriteWork.id }?.lastBookmarkId!!
                    ) },
                    isFavorite = favoriteWork.bookmarkData != null ||
                            bookmarks?.find { it.illustId == favoriteWork.id }?.lastBookmarkId != null
                )
            }
        }
    }
}

@Composable
@Preview
fun FavoritePreviewScreen() {
    DevAndArtTheme {
        FavoriteScreen(
            navigateToDetail = {}
        )
    }
}