package com.example.devandart

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.data.local.entity.UserEntity
import com.example.devandart.ui.common.UiState
import com.example.devandart.ui.screen.login.ItemCookie
import com.example.devandart.ui.screen.login.ItemUiState
import com.example.devandart.ui.screen.login.UserItem
import com.example.devandart.ui.screen.login.toEntity
import com.example.devandart.ui.screen.login.toItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

/**
 * ViewModel For MainActivity
 * */
class MainViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<CookieEntity>> = MutableStateFlow(UiState.Loading)
    private val _uiStateUser: MutableStateFlow<UiState<UserEntity>> = MutableStateFlow(UiState.Loading)
    private val _uiStateHtml: MutableStateFlow<UiState<ResponseBody>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<CookieEntity>>
        get() = _uiState
    val uiStateUser: StateFlow<UiState<UserEntity>>
        get() = _uiStateUser
    val uiStateHtml: StateFlow<UiState<ResponseBody>>
        get() = _uiStateHtml

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

    fun getUser() {
        viewModelScope.launch {
            repository.getUserFromDb()
                .catch {
                    _uiStateUser.value = UiState.Error(it.message.toString())
                }
                .collect { userResponse ->
                    _uiStateUser.value = userResponse
                }
        }
    }

    fun getHTML() {
        viewModelScope.launch {
            repository.getHTML()
                .catch {
                    _uiStateHtml.value = UiState.Error(it.message.toString())
                }
                .collect {cookieResponse ->
                    _uiStateHtml.value = cookieResponse
                }
        }
    }
    private fun validateInput(uiState: ItemCookie): Boolean {
        return with(uiState) {cookie.isNotBlank() && tokenCsrf.isNotBlank()}
    }
    fun saveUserDb(userItem: UserItem) {
        viewModelScope.launch {
            repository.saveUser(userItem.toEntity())
        }
    }
    fun updateCookieDb(cookieItem: ItemCookie) {
        if (validateInput(cookieItem)) {
            viewModelScope.launch {
                repository.updateCookie(cookieItem.toItem())
            }
        }
    }
}