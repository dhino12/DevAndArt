package com.example.devandart.ui.screen.home.Fixiv.illustrations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.ResultsDailyRankRecommended
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.data.remote.response.ResultsRecommended
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class IllustrationsViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ResultsItemIllustration>>> = MutableStateFlow(UiState.Loading)
    private val _uiStateRecommended: MutableStateFlow<UiState<List<ResultsRecommended>>> = MutableStateFlow(UiState.Loading)
    private val _uiStateDailyRank: MutableStateFlow<UiState<List<ResultsDailyRankRecommended>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<ResultsItemIllustration>>>
        get() = _uiState
    val uiStateRecommended: StateFlow<UiState<List<ResultsRecommended>>>
        get() = _uiStateRecommended
    val uiStateDailyRank: StateFlow<UiState<List<ResultsDailyRankRecommended>>>
        get() = _uiStateDailyRank

    fun getAllIllustrations() {
        viewModelScope.launch {
            repository.getAllIllustrations()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { illustration ->
                    _uiState.value = UiState.Success(illustration)
                }
        }
    }

    fun getRecommendedIllustrations() {
        viewModelScope.launch {
            repository.getRecommendedIllustrations()
                .catch {
                    _uiStateRecommended.value = UiState.Error(it.message.toString())
                }
                .collect { recommendedIllustrations ->
                    _uiStateRecommended.value = recommendedIllustrations
                }
        }
    }

    fun getDailyRank() {
        viewModelScope.launch {
            repository.getDailyRank()
                .catch {
                    _uiStateDailyRank.value = UiState.Error(it.message.toString())
                }
                .collect { dailyRank ->
                    _uiStateDailyRank.value = dailyRank
                }
        }
    }
}