package com.example.devandart.ui.screen.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.ResultSearchContent
import com.example.devandart.data.remote.response.ResultSuggestTagItem
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ResultSuggestTagItem>> =
        MutableStateFlow(UiState.Loading)

    private val _uiStateSearchKeyword: MutableStateFlow<UiState<ResultSearchContent>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ResultSuggestTagItem>>
        get() = _uiState
    val uiStateSearchKeyword: StateFlow<UiState<ResultSearchContent>>
        get() = _uiStateSearchKeyword
    var uiSearchValue by mutableStateOf("")
        private set
    fun getAllTagRecommend() {
        viewModelScope.launch {
            repository.getAllTagRecommend()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { illustration ->
                    _uiState.value = illustration
                }
        }
    }
    fun getSearchByKeyword(keyword: String) {
        viewModelScope.launch {
            repository.getSearchByKeyword(keyword = keyword)
                .catch {
                    _uiStateSearchKeyword.value = UiState.Error(it.message.toString())
                }
                .collect { illustration ->
                    _uiStateSearchKeyword.value = illustration
                }
        }
    }

    fun updateSearchValueText(keyword: String) {
        uiSearchValue = keyword
    }
}