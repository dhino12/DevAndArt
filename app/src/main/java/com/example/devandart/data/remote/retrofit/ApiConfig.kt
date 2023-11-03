package com.example.devandart.data.remote.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    var BASE_URL = "https://www.pixiv.net/"

    fun getApiService(cookie:String, userAgent: String, tokenCsrf: String): ApiService {
        Log.e("cookie API SERVICE", cookie)
        Log.e("userAgent", userAgent)
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor {chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("cookie", cookie)
                    .header("User-Agent", userAgent)
                    .header("Referer", "https://www.pixiv.net/")
                    .header("X-Csrf-Token", tokenCsrf)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}