package com.example.devandart.ui.screen.home.Fixiv.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MangaViewModel (private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<ResultsItemIllustration>> = MutableStateFlow(
        UiState.Loading)

    val uiState: StateFlow<UiState<ResultsItemIllustration>>
        get() = _uiState

    fun getAllMangas() {
        viewModelScope.launch {
            repository.getAllMangas()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { illustration ->
                    _uiState.value = illustration
                }
        }
    }
}