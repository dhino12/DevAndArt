package com.example.devandart.ui.screen.home.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.remote.ArtworkRepository
import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AuthCookiesResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AuthCookiesResponse>>
        get() = _uiState

    fun authCookie(cookie:String, userAgent:String) {
        viewModelScope.launch {
            repository.authCookies(cookie, userAgent)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {cookieResponse ->
                    _uiState.value = UiState.Success(cookieResponse)
                }
        }
    }
}