package com.example.devandart.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: ArtworkRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<AuthCookiesResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AuthCookiesResponse>>
        get() = _uiState
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateItem(itemCookie: ItemCookie) {
        itemUiState = ItemUiState(itemCookie = itemCookie, isEntryValid = validateInput(itemCookie))
    }
    suspend fun saveItem() {
        if (validateInput()) {
            repository.saveCookie(itemUiState.itemCookie.toItem())
        }
    }


    private fun validateInput(uiState: ItemCookie = itemUiState.itemCookie): Boolean {
        return with(uiState) {cookie.isNotBlank()}
    }
    fun authCookie(cookie:String, userAgent:String) {
        viewModelScope.launch {
            repository.authCookies(cookie, userAgent)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect {cookieResponse ->
                    _uiState.value = cookieResponse
                }
        }
    }

    fun setNewCookie(cookie: CookieEntity) {
        viewModelScope.launch {
            repository.saveCookie(cookie)
        }
    }
}

/**
 * Represents Ui State for an Item.
 */
data class ItemUiState(
    val itemCookie: ItemCookie = ItemCookie(),
    val isEntryValid: Boolean = false
)

data class ItemCookie(
    val id: Int = 0,
    val cookie: String = "",
)

fun ItemCookie.toItem(): CookieEntity = CookieEntity(
    id = id,
    cookie = cookie,
)