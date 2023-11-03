package com.example.devandart.ui.screen.home.Fixiv.illustrations

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.FavoriteDeleteRequest
import com.example.devandart.data.remote.response.FavoriteRequest
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class IllustrationsViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ResultsItemIllustration>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<ResultsItemIllustration>>
        get() = _uiState

    private val _uiStateFav: MutableStateFlow<UiState<ResultItemFavorite>> = MutableStateFlow(UiState.Loading)
    val uiStateFav: StateFlow<UiState<ResultItemFavorite>>
        get() = _uiStateFav

    var uiStateFavorite: UiState<ResultItemFavorite> by mutableStateOf(UiState.Loading)
    private var uiStateDeleteFavorite: UiState<FavoriteDeleteRequest> by mutableStateOf(UiState.Loading)

    fun getAllIllustrations() {
        viewModelScope.launch {
            repository.getAllIllustrations()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { illustration ->
                    _uiState.value = illustration
                }
        }
    }

    fun setFavorite(postData: ItemFavorite) {
        Log.e("FAVORITE SET", postData.toString())
        viewModelScope.launch {
            uiStateFavorite = repository.setFavorite(postData.toFavoriteRequest())
        }
    }

    fun getFavoriteSetResponse() {
        viewModelScope.launch {
            flow {
                emit(UiState.Success(uiStateFavorite))
            }
                .catch {
                    _uiStateFav.value = UiState.Error(it.message ?: "")
                }
                .collect {
                _uiStateFav.value = it.data
            }
        }
    }

    fun deleteFavorite(idBookmark: String) {
        viewModelScope.launch {
            uiStateDeleteFavorite = repository.deleteFavorite(idBookmark)
        }
    }
}

data class ItemFavorite(
    val illustId: String? = "",

    val restrict: Int? = 0,

    val comment: String? = "",

    val tags: List<String>? = listOf(),

    val bookmarkId: String? = "",

    val userId: String? = "",

    val username: String? = "",

    val title: String? = "",
)

fun ItemFavorite.toFavoriteRequest(): FavoriteRequest = FavoriteRequest(
    illust_Id = illustId,
    restrict = restrict,
    comment = comment,
    tags = tags
)