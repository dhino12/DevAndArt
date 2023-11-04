package com.example.devandart.ui.screen.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.devandart.R
import com.example.devandart.data.remote.response.ResultCommentByIllustration
import com.example.devandart.data.remote.response.ResultCommentItem
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultUserProfile
import com.example.devandart.data.remote.response.ResultsRelatedResponse
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.CommentData
import com.example.devandart.ui.component.ItemCard.ItemCardComment
import com.example.devandart.ui.component.ItemCard.ItemCardIllustration
import com.example.devandart.ui.component.ItemCard.ItemProfile
import com.example.devandart.ui.component.ItemCard.ProfileData
import com.example.devandart.ui.screen.ViewModelFactory
import com.example.devandart.ui.screen.home.Fixiv.illustrations.ItemFavorite
import com.example.devandart.ui.theme.DevAndArtTheme
import com.example.devandart.utils.gridContentItem
import com.example.devandart.utils.gridItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import de.charlex.compose.HtmlText

@Composable
fun DetailScreen(
    id: String,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateBack: () -> Unit = {},
    navigateToDetail: (String) -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    var detailData : ResultIllustrationDetail? = null
    var userProfileData: ResultUserProfile? = null
    var commentByIllustration: ResultCommentByIllustration? = null
    var relatedArtwork: ResultsRelatedResponse? = null
    var isFavorite by remember { mutableStateOf(ResultItemFavorite()) }

    viewModel.uiStateDetail.collectAsState(initial = UiState.Loading).value.let {
        uiState -> when(uiState) {
            is UiState.Loading -> {
                // LoadingScreen(loading = loading)
                viewModel.getDetail(id)
            }
            is UiState.Success -> {
                detailData = uiState.data
            }
            is UiState.Error -> {
                Log.e("illust", uiState.errorMessage)
            }
        }
    }

    viewModel.uiUserProfile.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                // LoadingScreen(loading = loading)
                detailData?.userId?.let { viewModel.getUseProfile(it) }
            }
            is UiState.Success -> {
                userProfileData = uiState.data
            }
            is UiState.Error -> {
                Log.e("userProfileDetail", uiState.errorMessage)
            }
        }
    }

    viewModel.uiCommentByIllustration.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                // LoadingScreen(loading = loading)
                detailData?.illustId?.let { viewModel.getCommentByIllustrationId(it) }
            }
            is UiState.Success -> {
                commentByIllustration = uiState.data
            }
            is UiState.Error -> {
                Log.e("itemByUserProfileDetail", uiState.errorMessage)
            }
        }
    }

    viewModel.uiRelatedArtworks.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                // LoadingScreen(loading = loading)
                detailData?.illustId?.let { viewModel.getRelatedArtworks(it) }
            }
            is UiState.Success -> {
                relatedArtwork = uiState.data
            }
            is UiState.Error -> {
                Log.e("itemByUserProfileDetail", uiState.errorMessage)
            }
        }
    }

    viewModel.uiStateFav.collectAsState(initial = UiState.Loading).value.let { uiStateFavorite ->
        when(uiStateFavorite) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                isFavorite = uiStateFavorite.data
                // Toast.makeText(LocalContext.current, "Success Favorite", Toast.LENGTH_SHORT).show()
                Log.e("isFavorites", isFavorite.toString())
            }
            is UiState.Error -> {
                Toast.makeText(LocalContext.current, "Error ${uiStateFavorite.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    Log.e("commentScreen", commentByIllustration?.comments?.size.toString())
    Log.e("detailScreen", detailData?.alt.toString())
    Log.e("userProfile", userProfileData?.name.toString())

//    SideEffect {
//        systemUiController.setSystemBarsColor(color = Color.Black)
//    }
    if (userProfileData != null && detailData != null && commentByIllustration != null && relatedArtwork != null) {
        var bookmark by remember { mutableStateOf(detailData?.bookmarkData != null) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    if (bookmark) {
                        bookmark = false;
                        viewModel.deleteFavorite(detailData?.bookmarkData?.id ?: "")
                    } else {
                        bookmark = true;
                        viewModel.setFavorite(
                            ItemFavorite(
                                illustId = detailData?.bookmarkData?.id,
                                restrict = 0,
                                tags = listOf(),
                                comment = "",
                            )
                        )
                    }
                }) {
                    Image(
                        painter = painterResource(if (bookmark) R.drawable.loved else R.drawable.love),
                        contentDescription = null
                    )
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                DetailContent(
                    userData = userProfileData!!,
                    relatedArtwork = relatedArtwork!!,
                    detailData = detailData!!,
                    commentsData = commentByIllustration!!,
                    navigateToDetail = navigateToDetail,
                )
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { navigateBack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    userData: ResultUserProfile,
    relatedArtwork: ResultsRelatedResponse,
    detailData: ResultIllustrationDetail,
    commentsData: ResultCommentByIllustration,
    navigateToDetail: (String) -> Unit = {},
) {
    LazyColumn {
        gridItems(
            modifier = Modifier.fillMaxSize(),
            count = detailData.pageCount ?: 1,
            nColumns = 1
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .setHeader("Referer", "http://www.pixiv.net/")
                    .data(detailData.urls?.original?.replace("_p0_", "_p${it}_"))
                    .crossfade(false)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loved),
                modifier = Modifier.fillMaxSize(),
            )
        }
        item {
            Column(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = modifier
                        .padding(horizontal = 18.dp, vertical = 12.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        ItemProfile(
                            dataProfile = userData.toProfileData(detailData.userAccount) // username / userdata
                        ) {
                            Row (
                                modifier = modifier.padding(vertical = 12.dp)
                            ) {
                                Text(
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    text = detailData.createDate ?: "2023-08-18 06:13"
                                )
                                Text(
                                    modifier = modifier.padding(start = 12.dp, end = 2.dp),
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.ExtraBold,
                                    text = detailData.viewCount.toString() ?: "12131"
                                )
                                Text(
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    text = "Views"
                                )
                                Text(
                                    modifier = modifier.padding(start = 12.dp, end = 2.dp),
                                    fontSize = 12.sp,
                                    color = Color.Cyan,
                                    fontWeight = FontWeight.ExtraBold,
                                    text = detailData.likeCount.toString() ?: "7686"
                                )
                                Text(
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                    text = "Likes"
                                )
                            }
                        }
                        detailData.tagsBody?.tags?.size?.let {
                            FlowRow(
                                modifier = Modifier.padding(end = 8.dp, bottom = 18.dp),
                                maxItemsInEachRow = it,
                            ) {
                                detailData.tagsBody.tags?.forEach{ tag ->
                                    Text(
                                        modifier = modifier.padding(end = 8.dp,bottom = 6.dp),
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        text = tag.tag ?: ""
                                    )

                                    if (tag.romaji != null) {
                                        Text(
                                            modifier = modifier.padding(end = 8.dp,bottom = 6.dp),
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            text = tag.romaji ?: ""
                                        )
                                    }
                                }
                            }
                        }
                        HtmlText(
                            modifier = Modifier.padding(bottom = 10.dp),
                            text = detailData.description ?: "",
                            color = Color.DarkGray,
                        )
                        Divider(thickness = 1.dp)
                        Spacer(modifier = modifier.height(18.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            ItemProfile (
                                dataProfile = userData.toProfileData(),
                                modifier = modifier
                                    .width(48.dp)
                                    .height(48.dp),
                                showUsername = false
                            ){}
                            OutlinedButton(
                                onClick = { /*TODO*/ },
                                border = BorderStroke(width = 2.dp, color = Color.Unspecified)
                            ) {
                                Text(
                                    text = "Follow"
                                )
                            }
                        }
                        LazyRow(modifier = Modifier.padding(vertical = 8.dp)) {
                            items(detailData?.userIllusts?.values?.toList()?.filterNotNull() ?: emptyList()) {illustrationUser ->
                                AsyncImage(
                                    model = ImageRequest
                                        .Builder(context = LocalContext.current)
                                        .setHeader("Referer", "http://www.pixiv.net/")
                                        .data(illustrationUser.url)
                                        .crossfade(false)
                                        .build(),
                                    contentDescription = illustrationUser.alt,
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(id = R.drawable.ic_broken_image),
                                    placeholder = painterResource(id = R.drawable.loved),
                                    modifier = Modifier
                                        .padding(horizontal = 1.5.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .width(115.dp),
                                )
                            }
                        }
                        TextButton(
                            modifier = modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 8.dp),
                            onClick = { /*TODO*/ }
                        ) {
                            Text(text = "View Profile", fontWeight = FontWeight.Bold)
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "go to profile"
                            )
                        }
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
        gridContentItem(
            modifier = modifier
                .padding(horizontal = 18.dp, vertical = 2.dp)
                .fillMaxWidth(),
            count = commentsData.comments?.size ?: 0,
            nColumns = 1,
            beforeContent = {
                Column(modifier = it) {
                    Spacer(modifier = modifier.height(8.dp))
                    Text(text = "Comments", fontWeight = FontWeight.Bold)
                }
            },
            itemContent = {
                if (commentsData.comments != null) {
                    ItemCardComment(
                        modifier = Modifier.padding(top= 5.dp),
                        commentData = commentsData.comments[it].toCommentData()
                    )
                }
            },
            afterContent = {
                if (commentsData.comments?.isEmpty() == true) return@gridContentItem
                Column(modifier = it) {
                    OutlinedButton(
                        modifier = modifier.align(Alignment.CenterHorizontally),
                        onClick = { /*TODO*/ },
                        border = BorderStroke(width = 2.dp, color = Color.Unspecified)
                    ) {
                        Text(
                            text = "See more"
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "go to profile"
                        )
                    }
                    Divider(thickness = 1.dp)
                }
            }
        )
        gridContentItem(
            modifier = Modifier,
            count = relatedArtwork.illusts?.size ?: 1,
            nColumns = 2,
            beforeContent = {
                Column(modifier = it.padding(horizontal = 18.dp)) {
                    Spacer(modifier = modifier.height(5.dp))
                    Text(
                        modifier = modifier.padding(vertical = 10.dp),
                        text = "Releted Illustration",
                        fontWeight = FontWeight.Bold,
                    )
                }
            },
            itemContent = {
                ItemCardIllustration(
                    modifier = Modifier
                        .padding(bottom = 3.dp, end = 2.dp, start = 2.dp)
                        .clickable {
                            relatedArtwork.illusts?.get(it)?.id?.let { idArtwork ->
                                navigateToDetail(
                                    idArtwork
                                )
                            }
                        },
                    imageIllustration = relatedArtwork.illusts?.get(it)?.thumbnail,
                    isFavorite = false
                )
            }
        )
    }
}

fun ResultUserProfile.toProfileData(userName: String? = ""): ProfileData {
    return object : ProfileData {
        override val name: String
            get() = this@toProfileData.name ?: ""
        override val username: String
            get() = userName ?: ""
        override val imageUser: String?
            get() = this@toProfileData.image ?: ""
    }
}

fun ResultCommentItem.toCommentData(): CommentData {
    return object : CommentData {
        override val id: String
            get() = this@toCommentData.id ?: "0"
        override val userId: String
            get() = this@toCommentData.userId ?: "0"
        override val username: String
            get() = this@toCommentData.username ?: ""
        override val thumb: String
            get() = this@toCommentData.thumb ?: ""
        override val comment: String
            get() = this@toCommentData.comment ?: ""
        override val stampId: String?
            get() = this@toCommentData.stampId ?: ""
        override val commentDate: String
            get() = this@toCommentData.commentDate ?: ""
        override val commentUserId: String
            get() = this@toCommentData.commentUserId ?: ""
        override val hasReplies: String?
            get() = this@toCommentData.id ?: ""
    }
}

@Preview()
@Composable
fun DetailScreenPreview() {
    DevAndArtTheme {
        DetailScreen(id = "")
    }
}