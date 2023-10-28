package com.example.devandart.di

import android.content.Context
import com.example.devandart.data.remote.ArtworkRepository
import com.example.devandart.data.remote.retrofit.ApiConfig
import com.example.devandart.data.remote.retrofit.ApiService

object Injections {
    fun providerRepository(context: Context): ArtworkRepository {
        val apiService: ApiService = ApiConfig.getApiService()
        return ArtworkRepository.getInstance(apiService)
    }
}