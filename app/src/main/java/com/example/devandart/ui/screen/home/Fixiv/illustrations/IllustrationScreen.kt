package com.example.devandart.ui.screen.home.Fixiv.illustrations

import android.app.Activity
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devandart.R
import com.example.devandart.data.remote.response.RecommendedResponse
import com.example.devandart.data.remote.response.ResultsDailyRankRecommended
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.data.remote.response.ResultsRecommended
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.login.LoginActivity
import com.example.devandart.utils.gridItems
import com.google.gson.annotations.SerializedName

@Composable
fun IllustrationScreen (
    modifier: Modifier = Modifier,
    viewModel: IllustrationsViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (String) -> Unit,
) {
//    var loading by remember { mutableStateOf(true) }
    var loading = true
    var illustrations: List<ResultsItemIllustration>? = null
    var recommendedIllust : List<ResultsRecommended>? = null
    var dailyRank: List<ResultsDailyRankRecommended>? = null

//    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
//        uiState -> when(uiState) {
//            is UiState.Loading -> {
//                LoadingScreen(loading = loading)
//                viewModel.getAllIllustrations()
//            }
//            is UiState.Success -> {
//                illustrations = uiState.data
//            }
//            is UiState.Error -> {
//                Log.e("illust", uiState.errorMessage)
//            }
//        }
//    }
    viewModel.uiStateRecommended.collectAsState(initial = UiState.Loading).value.let {
        uiStateRecommended -> when(uiStateRecommended) {
            is UiState.Loading -> {
                LoadingScreen(loading = loading)
                viewModel.getRecommendedIllustrations()
            }
            is UiState.Success -> {
                recommendedIllust = uiStateRecommended.data
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiStateRecommended.errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("recommended", uiStateRecommended.errorMessage)
            }
        }
    }
    viewModel.uiStateDailyRank.collectAsState(initial = UiState.Loading).value.let {
            uiDailyRank -> when(uiDailyRank) {
            is UiState.Loading -> {
                LoadingScreen(loading = loading)
                viewModel.getDailyRank()
            }
            is UiState.Success -> {
                dailyRank = uiDailyRank.data
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, uiDailyRank.errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("dailyRankScreen", uiDailyRank.errorMessage)
            }
        }
    }
    if (!illustrations.isNullOrEmpty() || !recommendedIllust.isNullOrEmpty() || !dailyRank.isNullOrEmpty()) {
        loading = false
        IllustrationContent(
            modifier = modifier,
            illustrations = illustrations ?: listOf(),
            recommendedIllust = recommendedIllust ?: listOf(),
            dailyRank = dailyRank ?: listOf(),
            navigateToDetail = navigateToDetail
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
    illustrations: List<ResultsItemIllustration> = listOf(),
    recommendedIllust: List<ResultsRecommended> = listOf(),
    dailyRank: List<ResultsDailyRankRecommended> = listOf(),
    navigateToDetail: (String) -> Unit,
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
                items(dailyRank, key = { illustKey -> illustKey.id!! }) {dailyRankIllustration ->
                    ItemCardIllustration(
                        modifier = Modifier
                            .padding(bottom = 3.dp, end = 4.dp, start = 3.dp),
                        imageIllustration = dailyRankIllustration.thumbnail
                    )
                }
            }
        }
        item {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "Recommended")
            }
        }
        gridItems(
            modifier = Modifier.padding(horizontal = 4.dp),
            count = recommendedIllust.size,
            nColumns = 2
        ) {
            ItemCardIllustration(
                modifier = Modifier
                    .padding(bottom = 3.dp, end = 2.dp, start = 1.dp)
                    .clickable {
                               navigateToDetail(recommendedIllust[it].id.toString())
                    },
                imageIllustration = recommendedIllust[it].thumbnail
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