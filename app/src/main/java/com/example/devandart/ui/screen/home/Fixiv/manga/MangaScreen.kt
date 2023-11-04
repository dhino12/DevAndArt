package com.example.devandart.ui.screen.home.Fixiv.manga

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemCardManga
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.ui.screen.home.Fixiv.illustrations.ItemFavorite
import com.example.devandart.ui.screen.home.Fixiv.illustrations.LoadingScreen
import com.example.devandart.utils.gridItems

@Composable
fun MangaScreen(
    drawerState:DrawerState,
    viewModel: MangaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    var loading = true
    var isFavorite by remember { mutableStateOf(mutableListOf<ResultItemFavorite>()) }
    var recommendedManga : MutableList<ResultItemIllustration>? = mutableListOf()
    var dailyRank: MutableList<ResultItemIllustration>? = mutableListOf()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                LoadingScreen(loading = loading)
                viewModel.getAllMangas()
            }
            is UiState.Success -> {
                uiState.data.thumbnails?.illusts?.forEach { thumbnail ->
                    if (uiState.data.page?.recommend?.idIllustrations?.contains(thumbnail.id) == true) {
                        recommendedManga?.add(thumbnail)
                    }
                    if (uiState.data.page?.rankings?.items?.find { thumbnail.id == it.id }?.id == thumbnail.id) {
                        dailyRank?.add(thumbnail)
                    }
                }
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("recommended", uiState.errorMessage)
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

    if (!recommendedManga.isNullOrEmpty() && !dailyRank.isNullOrEmpty()) {
        MangaContent(
            recommendedManga = recommendedManga ?: listOf(),
            dailyRank = dailyRank ?: listOf(),
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
}

@Composable
fun MangaContent(
    modifier: Modifier = Modifier,
    recommendedManga: List<ResultItemIllustration> = listOf(),
    dailyRank: List<ResultItemIllustration> = listOf(),
    navigateToDetail: (String) -> Unit = {},
    updateStateFavorite: (ItemFavorite) -> Unit,
    deleteFavorite: (String) -> Unit,
    bookmarks: List<ResultItemFavorite>? = null,
) {
    LazyColumn {
        item {
            TextButton(onClick = {}) {
                Image(
                    modifier = Modifier.width(35.dp),
                    painter = painterResource(R.drawable.crown),
                    contentDescription = "ranking"
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "Rankings",
                )
            }
            LazyRow(
                userScrollEnabled = true,
                contentPadding = PaddingValues(5.dp),
            ) {
                items(dailyRank) { dailyRankItem ->
                    ItemCardIllustration(
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 4.dp, start = 3.dp)
                            .clickable { navigateToDetail(dailyRankItem.id.toString()) },
                        imageIllustration = dailyRankItem.url,
                        onFavorite = {updateStateFavorite(ItemFavorite(
                            illustId = dailyRankItem.id,
                        ))},
                        onDeleteFavorite = { deleteFavorite(
                            dailyRankItem.bookmarkData?.id ?:
                            bookmarks?.find { it.illustId == dailyRankItem.id }?.lastBookmarkId!!
                        ) },
                        isFavorite = dailyRankItem.bookmarkData != null ||
                                bookmarks?.find { it.illustId == dailyRankItem.id }?.lastBookmarkId != null
                    )
                }
            }
        }
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Image(
                    modifier = Modifier.width(20.dp),
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Recommended"
                )
                Text(
                    modifier = Modifier.padding(start = 15.dp),
                    text = "Recommended"
                )
            }
        }
        gridItems(
            modifier = Modifier,
            count = recommendedManga.size,
            nColumns = 2,
        ) {
            ItemCardManga(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
                    .clickable { navigateToDetail(recommendedManga[it].id.toString()) },
                title = recommendedManga[it].title,
                shortDescription = recommendedManga[it].tags?.joinToString(", "),
                imageIllustration = recommendedManga[it].url,
                favoriteCount = recommendedManga[it].bookmarkData?.id,
                onFavorite = {updateStateFavorite(ItemFavorite(
                    illustId = recommendedManga[it].id,
                ))},
                onDeleteFavorite = { deleteFavorite(
                    recommendedManga[it].bookmarkData?.id ?:
                    bookmarks?.find {
                            bookarmk -> bookarmk.illustId == recommendedManga[it].id
                    }?.lastBookmarkId!!
                ) },
                isFavorite = recommendedManga[it].bookmarkData != null ||
                        bookmarks?.find {
                                bookmark -> bookmark.illustId == recommendedManga[it].id
                        }?.lastBookmarkId != null
            )
        }
//        item {
//            TextButton(onClick = { /*TODO*/ }) {
//                Text(text = "Newest")
//            }
//        }
//        gridItems(
//            modifier = Modifier.padding(horizontal = 4.dp),
//            count = 4,
//            nColumns = 2,
//        ) {
//            ItemCardManga(
//                modifier = Modifier
//                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
//            )
//        }
    }
}