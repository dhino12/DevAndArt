package com.example.devandart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * ViewModel For MainActivity
 * */
class MainViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CookieEntity>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<CookieEntity>>
        get() = _uiState

    fun getCookie() {
        viewModelScope.launch {
            repository.getCookieFromDb()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {cookieResponse ->
                    _uiState.value = cookieResponse
                }
        }
    }
}