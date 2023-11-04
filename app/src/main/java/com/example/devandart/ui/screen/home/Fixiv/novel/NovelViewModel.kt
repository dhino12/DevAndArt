package com.example.devandart.ui.screen.home.Fixiv.novel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.FavoriteDeleteRequest
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.screen.home.Fixiv.illustrations.ItemFavorite
import com.example.devandart.ui.screen.home.Fixiv.illustrations.toFavoriteRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class NovelViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ResultsItemIllustration>> = MutableStateFlow(
        UiState.Loading)
    private val _uiStateFav: MutableStateFlow<UiState<ResultItemFavorite>> = MutableStateFlow(
        UiState.Loading)

    val uiState: StateFlow<UiState<ResultsItemIllustration>>
        get() = _uiState
    val uiStateFav: StateFlow<UiState<ResultItemFavorite>>
        get() = _uiStateFav

    private var uiStateDeleteFavorite: UiState<FavoriteDeleteRequest> by mutableStateOf(UiState.Loading)

    fun getAllNovels() {
        viewModelScope.launch {
            repository.getAllNovels()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { illustration ->
                    _uiState.value = illustration
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
