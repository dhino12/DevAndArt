package com.example.devandart.ui.screen.home.Fixiv.manga

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemCardManga
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.ui.screen.home.Fixiv.illustrations.LoadingScreen
import com.example.devandart.utils.gridItems

@Composable
fun MangaScreen(
    viewModel: MangaViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    var loading = true
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

    if (!recommendedManga.isNullOrEmpty() && !dailyRank.isNullOrEmpty()) {
        MangaContent(
            recommendedManga = recommendedManga ?: listOf(),
            dailyRank = dailyRank ?: listOf(),
            navigateToDetail = navigateToDetail,
        )
    }
}

@Composable
fun MangaContent(
    modifier: Modifier = Modifier,
    recommendedManga: List<ResultItemIllustration> = listOf(),
    dailyRank: List<ResultItemIllustration> = listOf(),
    navigateToDetail: (String) -> Unit = {}
) {
    LazyColumn {
        item {
            TextButton( onClick = { /*TODO*/ } ) {
                Text(text = "Rankings", fontWeight = FontWeight.Bold)
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
                        isFavorite = false
                    )
                }
            }
        }
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Recommended", fontWeight = FontWeight.Bold)
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
                favoriteCount = recommendedManga[it].bookmarkData?.id
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