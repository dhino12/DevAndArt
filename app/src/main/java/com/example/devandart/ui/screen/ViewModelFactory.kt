package com.example.devandart.ui.screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.di.Injections
import com.example.devandart.ui.screen.detail.DetailViewModel
import com.example.devandart.ui.screen.home.Fixiv.illustrations.IllustrationsViewModel
import com.example.devandart.MainViewModel
import com.example.devandart.ui.screen.favorite.FavoriteViewModel
import com.example.devandart.ui.screen.home.Fixiv.manga.MangaViewModel
import com.example.devandart.ui.screen.home.Fixiv.novel.NovelViewModel
import com.example.devandart.ui.screen.login.LoginViewModel
import com.example.devandart.ui.screen.logout.LogoutViewModel
import com.example.devandart.ui.screen.search.SearchViewModel

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
        else if (modelClass.isAssignableFrom(MangaViewModel::class.java)) {
            return  MangaViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return  SearchViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return  FavoriteViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(LogoutViewModel::class.java)) {
            return  LogoutViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(NovelViewModel::class.java)) {
            return  NovelViewModel(repository) as T
        }
        else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return  MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context, cookie: String = "", csrfTokenApi:String = ""): ViewModelFactory =
            instance ?: synchronized(this) {
                Log.e("CookieVMFactory", cookie)
                instance ?: ViewModelFactory(Injections.providerRepository(
                    context = context, cookie = cookie, csrfTokenApi = csrfTokenApi
                ))
            }.also { instance = it }

        fun destroyInstance() {
            instance = null
        }
    }
}