package com.example.devandart.ui.screen.home.Fixiv.novel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultItemContensNovel
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemCardNovel
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationContent
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.ui.screen.home.Fixiv.illustrations.ItemFavorite
import com.example.devandart.ui.screen.home.Fixiv.illustrations.LoadingScreen

@Composable
fun NovelScreen(
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    navigateToDetail: (String) -> Unit,
    viewModel: NovelViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
) {
    var loading = true
    var isFavorite by remember { mutableStateOf(mutableListOf<ResultItemFavorite>()) }
    var recommendedNovels : MutableList<ResultItemContensNovel>? = mutableListOf()
    var dailyRank: MutableList<ResultItemContensNovel>? = mutableListOf()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
        is UiState.Loading -> {
            LoadingScreen(loading = loading)
            viewModel.getAllNovels()
        }
        is UiState.Success -> {
            uiState.data.thumbnails?.novels?.forEach { thumbnail ->
                if (uiState.data.page?.recommend?.idIllustrations?.contains(thumbnail.id) == true) {
                    recommendedNovels?.add(thumbnail)
                }
                if (uiState.data.page?.rankings?.items?.find {
                        thumbnail.id == it.id
                    }?.id == thumbnail.id) {
                    dailyRank?.add(thumbnail)
                }
                Log.e("NovelsData", recommendedNovels.toString())
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

    if (!recommendedNovels.isNullOrEmpty() && !dailyRank.isNullOrEmpty()) {
        NovelContent(
            modifier = Modifier,
            recommendedNovels = recommendedNovels ?: listOf(),
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
fun NovelContent(
    modifier: Modifier = Modifier,
    recommendedNovels: List<ResultItemContensNovel> = listOf(),
    dailyRank: List<ResultItemContensNovel> = listOf(),
    navigateToDetail: (String) -> Unit,
    updateStateFavorite: (ItemFavorite) -> Unit,
    deleteFavorite: (String) -> Unit,
    bookmarks: List<ResultItemFavorite>? = null,
){
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
                userScrollEnabled = true
            ) {
                items(dailyRank) {dailyRankNovel ->
                    ItemCardIllustration(
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 4.dp, start = 3.dp)
                            .clickable {  },
                        imageIllustration = dailyRankNovel.url?.toString(),
                        onFavorite = {updateStateFavorite(ItemFavorite(
                            illustId = dailyRankNovel.id,
                        ))},
                        onDeleteFavorite = { deleteFavorite(
                            dailyRankNovel.bookmarkData?.id ?:
                            bookmarks?.find { it.illustId == dailyRankNovel.id }?.lastBookmarkId!!
                        ) },
                        isFavorite = dailyRankNovel.bookmarkData != null ||
                                bookmarks?.find { it.illustId == dailyRankNovel.id }?.lastBookmarkId != null
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
        items(recommendedNovels) {recommendNovel ->
            ItemCardNovel(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                    .clickable {  },
                imageIllustration = recommendNovel.url?.toString(),
                onFavorite = {updateStateFavorite(ItemFavorite(
                    illustId = recommendNovel.id,
                ))},
                onDeleteFavorite = { deleteFavorite(
                    recommendNovel.bookmarkData?.id ?:
                    bookmarks?.find { it.illustId == recommendNovel.id }?.lastBookmarkId!!
                ) },
                isFavorite = recommendNovel.bookmarkData != null ||
                        bookmarks?.find { it.illustId == recommendNovel.id }?.lastBookmarkId != null
            ) {
                Column {
                    Text(
                        text = recommendNovel.title ?: "Title",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = "by ${recommendNovel.username}" ?: "by Author",
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = recommendNovel.description ?: "Description Short",
                        fontSize = 10.sp,
                    )
                }
            }
        }
    }
}