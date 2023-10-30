package com.example.devandart.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.remote.response.ResultCommentByIllustration
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultItemIllustrationByUser
import com.example.devandart.data.remote.response.ResultUserProfile
import com.example.devandart.ui.common.UiState
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

    private val _uiCommentByIllustration: MutableStateFlow<UiState<List<ResultCommentByIllustration>>> =
        MutableStateFlow(UiState.Loading)

    // ========== public variables
    val uiStateDetail: StateFlow<UiState<ResultIllustrationDetail>>
        get() = _uiDetailIllustration
    val uiUserProfile: StateFlow<UiState<ResultUserProfile>>
        get() = _uiUserProfile
    val uiItemByUserProfile: StateFlow<UiState<ResultItemIllustrationByUser>>
        get() = _uiItemByUserProfile

    val uiCommentByIllustration: StateFlow<UiState<List<ResultCommentByIllustration>>>
        get() = _uiCommentByIllustration

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

    /**
     * illustration by artist (user) related
     */
    fun getItemByUserId(id: String) {
        viewModelScope.launch {
            repository.getIllustrationByUserId(id)
                .catch {
                    _uiItemByUserProfile.value = UiState.Error(it.message.toString())
                }
                .collect {userData ->
                    _uiItemByUserProfile.value = userData
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
}