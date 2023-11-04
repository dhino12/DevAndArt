package com.example.devandart.ui.screen.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.FavoriteDeleteRequest
import com.example.devandart.data.remote.response.ResultCommentByIllustration
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemIllustrationByUser
import com.example.devandart.data.remote.response.ResultUserProfile
import com.example.devandart.data.remote.response.ResultsRelatedResponse
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.screen.home.Fixiv.illustrations.ItemFavorite
import com.example.devandart.ui.screen.home.Fixiv.illustrations.toFavoriteRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ArtworkRepository): ViewModel() {
    // ========== private variables
    private val _uiDetailIllustration: MutableStateFlow<UiState<ResultIllustrationDetail>> =
        MutableStateFlow(UiState.Loading)

    private val _uiUserProfile: MutableStateFlow<UiState<ResultUserProfile>> =
        MutableStateFlow(UiState.Loading)

    private val _uiItemByUserProfile: MutableStateFlow<UiState<ResultItemIllustrationByUser>> =
        MutableStateFlow(UiState.Loading)

    private val _uiCommentByIllustration: MutableStateFlow<UiState<ResultCommentByIllustration>> =
        MutableStateFlow(UiState.Loading)

    private val _uiRelatedArtworks: MutableStateFlow<UiState<ResultsRelatedResponse>> =
        MutableStateFlow(UiState.Loading)

    private val _uiStateFav: MutableStateFlow<UiState<ResultItemFavorite>> =
        MutableStateFlow(UiState.Loading)

    // ========== public variables
    val uiStateDetail: StateFlow<UiState<ResultIllustrationDetail>>
        get() = _uiDetailIllustration
    val uiUserProfile: StateFlow<UiState<ResultUserProfile>>
        get() = _uiUserProfile
    val uiCommentByIllustration: StateFlow<UiState<ResultCommentByIllustration>>
        get() = _uiCommentByIllustration
    val uiRelatedArtworks: StateFlow<UiState<ResultsRelatedResponse>>
        get() = _uiRelatedArtworks

    val uiStateFav: StateFlow<UiState<ResultItemFavorite>>
        get() = _uiStateFav

    private var uiStateDeleteFavorite: UiState<FavoriteDeleteRequest> by mutableStateOf(UiState.Loading)

    fun getDetail(id: String) {
        viewModelScope.launch {
            repository.getIllustrationDetail(id)
                .catch {
                    _uiDetailIllustration.value = UiState.Error(it.message.toString())
                }
                .collect { detail ->
                    _uiDetailIllustration.value = detail
                }
        }
    }

    fun getUseProfile(id: String) {
        viewModelScope.launch {
            repository.getUserById(id)
                .catch {
                    _uiUserProfile.value = UiState.Error(it.message.toString())
                }
                .collect {userData ->
                    _uiUserProfile.value = userData
                }
        }
    }
    fun getCommentByIllustrationId(id: String) {
        viewModelScope.launch {
            repository.getCommentByIllustrationId(id)
                .catch {
                    _uiCommentByIllustration.value = UiState.Error(it.message.toString())
                }
                .collect {userData ->
                    _uiCommentByIllustration.value = userData
                }
        }
    }

    fun getRelatedArtworks(id: String) {
        viewModelScope.launch {
            repository.getRelatedArtwork(id)
                .catch {
                    _uiRelatedArtworks.value = UiState.Error(it.message.toString())
                }
                .collect {userData ->
                    _uiRelatedArtworks.value = userData
                }
        }
    }

    fun setFavorite(postData: ItemFavorite) {
        viewModelScope.launch {
            repository.setFavorite(postData.toFavoriteRequest(), postData.illustId ?:"" )
                .catch {
                    _uiStateFav.value = UiState.Error(it.message.toString())
                }
                .collect { favorite ->
                    _uiStateFav.value = favorite
                }
        }
    }

    fun deleteFavorite(idBookmark: String) {
        Log.e("Delete Id Bookmark", idBookmark)
        viewModelScope.launch {
            uiStateDeleteFavorite = repository.deleteFavorite(idBookmark)
        }
    }
}