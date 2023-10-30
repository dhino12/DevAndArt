package com.example.devandart.ui.screen.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultItemIllustrationByUser
import com.example.devandart.data.remote.response.ResultUserProfile
import com.example.devandart.data.remote.response.ResultsDailyRankRecommended
import com.example.devandart.data.remote.response.ResultsRecommended
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.component.ItemCard.CommentData
import com.example.devandart.ui.component.ItemCard.ItemCardComment
import com.example.devandart.ui.component.ItemCard.ItemProfile
import com.example.devandart.ui.component.ItemCard.ProfileData
import com.example.devandart.ui.screen.ViewModelFactory
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
    navigateToDetail: (String) -> Unit = {}
) {
    val systemUiController = rememberSystemUiController()
    var detailData : ResultIllustrationDetail? = null
    var userProfileData: ResultUserProfile? = null
    var illustrationByUser: ResultItemIllustrationByUser? = null
    var commentByIllustration: List<ResultCommentByIllustration>? = null

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
                detailData?.user?.idUser?.let { viewModel.getUseProfile(it) }
            }
            is UiState.Success -> {
                userProfileData = uiState.data
            }
            is UiState.Error -> {
                Log.e("userProfileDetail", uiState.errorMessage)
            }
        }
    }

    viewModel.uiItemByUserProfile.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                // LoadingScreen(loading = loading)
                detailData?.user?.idUser?.let { viewModel.getItemByUserId(it) }
            }
            is UiState.Success -> {
                illustrationByUser = uiState.data
            }
            is UiState.Error -> {
                Log.e("itemByUserProfileDetail", uiState.errorMessage)
            }
        }
    }

    viewModel.uiCommentByIllustration.collectAsState(initial = UiState.Loading).value.let {
            uiState -> when(uiState) {
            is UiState.Loading -> {
                // LoadingScreen(loading = loading)
                detailData?.illustrationId?.let { viewModel.getCommentByIllustrationId(it) }
            }
            is UiState.Success -> {
                commentByIllustration = uiState.data
            }
            is UiState.Error -> {
                Log.e("itemByUserProfileDetail", uiState.errorMessage)
            }
        }
    }

    SideEffect {
        systemUiController.setSystemBarsColor(color = Color.Black)
    }

    if (userProfileData != null && detailData != null && illustrationByUser != null && commentByIllustration != null) {
        DetailContent(
            userData = userProfileData!!,
            illustrationByUser = illustrationByUser!!,
            detailData = detailData!!,
            commentsData = commentByIllustration!!
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    userData: ResultUserProfile,
    illustrationByUser: ResultItemIllustrationByUser,
    detailData: ResultIllustrationDetail,
    commentsData: List<ResultCommentByIllustration>
) {
    LazyColumn {
        gridItems(
            modifier = Modifier.fillMaxSize(),
            count = detailData.urls?.size ?: 1,
            nColumns = 1
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .setHeader("Referer", "http://www.pixiv.net/")
                    .data(detailData.urls?.get(it)?.regular)
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
                            dataProfile = userData.toProfileData()
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
                                    text = detailData.view ?: "12131"
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
                                    text = detailData.like ?: "7686"
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
                            items(illustrationByUser.illusts) {illustrationUser ->
                                AsyncImage(
                                    model = ImageRequest
                                        .Builder(context = LocalContext.current)
                                        .setHeader("Referer", "http://www.pixiv.net/")
                                        .data(illustrationUser.thumb)
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
            count = commentsData.size,
            nColumns = 1,
            beforeContent = {
                Column(modifier = it) {
                    Spacer(modifier = modifier.height(8.dp))
                    Text(text = "Comments", fontWeight = FontWeight.Bold)
                }
            },
            itemContent = {
                ItemCardComment(
                    modifier = Modifier.padding(top= 5.dp),
                    commentData = commentsData[it].toCommentData()
                )
            },
            afterContent = {
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
//        gridContentItem(
//            modifier = Modifier,
//            count = tags.size,
//            nColumns = 2,
//            beforeContent = {
//                Column(modifier = it.padding(horizontal = 18.dp)) {
//                    Spacer(modifier = modifier.height(5.dp))
//                    Text(
//                        modifier = modifier.padding(vertical = 10.dp),
//                        text = "Releted Illustration"
//                    )
//                }
//            },
//            itemContent = {
//                ItemCardIllustration(
//                    modifier = Modifier
//                        .padding(bottom = 3.dp, end = 2.dp, start = 2.dp)
//                        .clickable { navigateToDetail(tags[it]) }
//                )
//            }
//        )
    }
}

fun ResultUserProfile.toProfileData(): ProfileData {
    return object : ProfileData {
        override val name: String
            get() = this@toProfileData.name ?: ""
        override val username: String
            get() = this@toProfileData.name ?: ""
        override val imageUser: String?
            get() = this@toProfileData.image ?: ""
    }
}

fun ResultCommentByIllustration.toCommentData(): CommentData {
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