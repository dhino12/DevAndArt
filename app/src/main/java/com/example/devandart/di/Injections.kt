package com.example.devandart.di

import android.content.Context
import android.util.Log
import com.example.devandart.data.ArtworkRepository
import com.example.devandart.data.local.room.ArtworkDatabase
import com.example.devandart.data.remote.retrofit.ApiConfig
import com.example.devandart.data.remote.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

object Injections {
    fun providerRepository(context: Context, cookie: String, csrfTokenApi: String): ArtworkRepository {
        val database = ArtworkDatabase.getDatabase(context)
        val dao = database.artworkDao()
//        val cookieValue = dao.getCookie()
//        Log.i("cookieFrom Injections", cookieValue.toString())
        val apiService: ApiService = ApiConfig.getApiService(
            cookie = cookie,
            userAgent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.46",
            tokenCsrf = csrfTokenApi
        )
        if (cookie.isNotBlank()) {
            ArtworkRepository.destroyInstance()
        }
        return ArtworkRepository.getInstance(apiService, dao)
    }
}