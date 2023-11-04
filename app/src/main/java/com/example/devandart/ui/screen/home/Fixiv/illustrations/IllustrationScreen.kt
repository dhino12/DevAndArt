package com.example.devandart.ui.screen.home.Fixiv.illustrations

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemProfileShorts
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.utils.gridItems
import com.example.devandart.utils.toOriginalImg

@Composable
fun IllustrationScreen (
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    viewModel: IllustrationsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
    var loading = true
    var isFavorite by remember { mutableStateOf(mutableListOf<ResultItemFavorite>()) }
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
                    if (uiState.data.page?.rankings?.items?.find {
                            thumbnail.id == it.id
                    }?.id == thumbnail.id) {
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
            bookmarks = isFavorite ?: null
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
                modifier = modifier.fillMaxWidth(),
                contentPadding = PaddingValues(5.dp),
            ) {
                items(dailyRank, key = { illustKey -> illustKey.id ?: 0 }) {dailyRankIllustration ->
                    Log.e("Image", dailyRankIllustration.url?.toOriginalImg().toString())
                    ItemCardIllustration(
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 4.dp, start = 3.dp)
                            .clickable {
                                navigateToDetail(dailyRankIllustration.id.toString())
                            },
                        imageIllustration = dailyRankIllustration.urls?.regular.toString(),
                        onFavorite = {updateStateFavorite(ItemFavorite(
                            illustId = dailyRankIllustration.id,
                        ))},
                        onDeleteFavorite = { deleteFavorite(
                            dailyRankIllustration.bookmarkData?.id ?:
                            bookmarks?.find { it.illustId == dailyRankIllustration.id }?.lastBookmarkId!!
                        ) },
                        isFavorite = dailyRankIllustration.bookmarkData != null ||
                                bookmarks?.find { it.illustId == dailyRankIllustration.id }?.lastBookmarkId != null
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
            count = recommendedIllust.size,
            nColumns = 2
        ) {indexBoxItem ->
            if (recommendedIllust[indexBoxItem].url?.isNullOrEmpty() == true) return@gridItems
            ItemCardIllustration(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
                    .clickable {
                        navigateToDetail(recommendedIllust[indexBoxItem].id.toString())
                    },
                imageIllustration = (
                        if (recommendedIllust[indexBoxItem].url.isNullOrEmpty()) ""
                        else recommendedIllust[indexBoxItem].urls?.regular
                ).toString(),
                onFavorite = {updateStateFavorite(ItemFavorite(
                    illustId = recommendedIllust[indexBoxItem].id,
                ))},
                onDeleteFavorite = { deleteFavorite(
                    recommendedIllust[indexBoxItem].bookmarkData?.id ?:
                    bookmarks?.find { it.illustId == recommendedIllust[indexBoxItem].id }?.lastBookmarkId!!
                ) },
                isFavorite = recommendedIllust[indexBoxItem].bookmarkData != null ||
                        bookmarks?.find { it.illustId == recommendedIllust[indexBoxItem].id }?.lastBookmarkId != null
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