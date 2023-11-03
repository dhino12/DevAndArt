package com.example.devandart.ui.screen.home.Fixiv.illustrations

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.data.remote.response.BookmarkData
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemProfileShorts
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.utils.gridItems
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@Composable
fun IllustrationScreen (
    modifier: Modifier = Modifier,
    viewModel: IllustrationsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    var loading = true
//    var isFavorite = false
    var isFavorite: ResultItemFavorite by remember {mutableStateOf(ResultItemFavorite())}
    var recommendedIllust : MutableList<ResultItemIllustration>? = mutableListOf()
    var dailyRank: MutableList<ResultItemIllustration>? = mutableListOf()

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
        is UiState.Loading -> {
            LoadingScreen(loading = loading)
            viewModel.getAllIllustrations()
        }
        is UiState.Success -> {
            uiState.data.thumbnails?.illusts?.forEach { thumbnail ->
                if (uiState.data.page?.recommend?.idIllustrations?.contains(thumbnail.id) == true) {
                    recommendedIllust?.add(thumbnail)
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
                viewModel.getFavoriteSetResponse()
            }
            is UiState.Success -> {
                isFavorite = uiStateFavorite.data
                Toast.makeText(LocalContext.current, "Favorite 💖", Toast.LENGTH_SHORT).show()
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, "Error ${uiStateFavorite.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    if (!recommendedIllust.isNullOrEmpty() && !dailyRank.isNullOrEmpty()) {
        loading = false
        IllustrationContent(
            modifier = modifier,
            recommendedIllust = recommendedIllust ?: listOf(),
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
            bookmark = isFavorite ?: null
        )
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
) {
    if (!loading) return
    Box (
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
fun IllustrationContent(
    modifier: Modifier = Modifier,
    recommendedIllust: List<ResultItemIllustration> = listOf(),
    dailyRank: List<ResultItemIllustration> = listOf(),
    navigateToDetail: (String) -> Unit,
    updateStateFavorite: (ItemFavorite) -> Unit,
    deleteFavorite: (String) -> Unit,
    bookmark: ResultItemFavorite? = null,
) {
    LazyColumn {
        item {
            TextButton( onClick = { /*TODO*/ } ) {
                Text(text = "Rankings")
            }
            LazyRow(
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(5.dp),
            ) {
                items(dailyRank, key = { illustKey -> illustKey.id ?: 0 }) {dailyRankIllustration ->
                    ItemCardIllustration(
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 4.dp, start = 3.dp),
                        imageIllustration = (if (dailyRankIllustration.url.isNullOrEmpty()) "" else dailyRankIllustration.url).toString(),
                        onFavorite = {updateStateFavorite(ItemFavorite(
                            illustId = dailyRankIllustration.id,
                        ))},
                        onDeleteFavorite = { deleteFavorite(dailyRankIllustration.bookmarkData?.id ?: bookmark?.lastBookmarkId!!) },
                        isFavorite = dailyRankIllustration.bookmarkData != null
                    ) {
                        Box {
                            Spacer(
                                modifier = Modifier
                                    .alpha(0.4F)
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color.Transparent, Color.Black),
                                            startY = 150.00F,
                                            endY = 550.00F,
                                        )
                                    )
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                            )
                            ItemProfileShorts(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                title = dailyRankIllustration.title,
                                username = dailyRankIllustration.username,
                                image = dailyRankIllustration.profileImageUrl
                            )
                        }
                    }
                }
            }
        }
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Recommended")
            }
        }
        gridItems(
            modifier = Modifier,
            count = recommendedIllust.size,
            nColumns = 2
        ) {
            if (recommendedIllust[it].url?.isNullOrEmpty() == true) return@gridItems
            ItemCardIllustration(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
                    .clickable {
                        navigateToDetail(recommendedIllust[it].id.toString())
                    },
                imageIllustration = (if (recommendedIllust[it].url.isNullOrEmpty()) "" else recommendedIllust[it].url).toString(),
                onFavorite = { updateStateFavorite(ItemFavorite(illustId = recommendedIllust[it].id)) },
                onDeleteFavorite = { deleteFavorite(recommendedIllust[it].id ?: "") },
                isFavorite = recommendedIllust[it].bookmarkData != null,
            )
        }

//        item {
//            TextButton(onClick = { /*TODO*/ }) {
//                Text(text = "Newest")
//            }
//        }
//        gridItems(
//            modifier = Modifier.padding(horizontal = 4.dp),
//            count = illustrations.size,
//            nColumns = 2
//        ) {index ->
//            ItemCardIllustration(
//                modifier = Modifier
//                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp),
//                imageIllustration = illustrations[index].thumbnail
//            )
//        }
    }
}