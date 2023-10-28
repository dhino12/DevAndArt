package com.example.devandart.ui.screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devandart.data.remote.ArtworkRepository
import com.example.devandart.di.Injections
import com.example.devandart.ui.screen.detail.DetailViewModel
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.ui.screen.home.login.LoginViewModel

class ViewModelFactory private constructor(private val repository: ArtworkRepository):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IllustrationsViewModel::class.java)) {
            return  IllustrationsViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return  LoginViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return  DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injections.providerRepository(context = context))
            }.also { instance = it }
    }
}