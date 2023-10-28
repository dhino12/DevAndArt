package com.example.devandart.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.remote.ArtworkRepository
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiDetailIllustration: MutableStateFlow<UiState<ResultIllustrationDetail>> =
        MutableStateFlow(UiState.Loading)

    val uiStateDetail: StateFlow<UiState<ResultIllustrationDetail>>
        get() = _uiDetailIllustration

    fun getDetail(id: String) {
        viewModelScope.launch {
            repository.getIllustrationDetail(id)
                .catch {
                    _uiDetailIllustration.value = UiState.Error(it.message.toString())
                }
                .collect { detail ->
                    _uiDetailIllustration.value = UiState.Success(detail)
                }
        }
    }
}