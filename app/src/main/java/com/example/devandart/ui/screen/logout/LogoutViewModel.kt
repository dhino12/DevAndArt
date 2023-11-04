package com.example.devandart.ui.screen.logout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.ui.screen.login.ItemCookie
import com.example.devandart.ui.screen.login.UserItem
import com.example.devandart.ui.screen.login.toEntity
import com.example.devandart.ui.screen.login.toItem
import kotlinx.coroutines.launch

class LogoutViewModel(private val repository: ArtworkRepository):ViewModel() {
    fun deleteUserDb(userItem: UserItem) {
        viewModelScope.launch {
            repository.deleteUser(userItem.toEntity())
        }
    }

    fun deleteCookieDb(cookieItem: ItemCookie) {
        viewModelScope.launch {
            repository.deleteCookie(cookieItem.toItem())
        }
    }
}