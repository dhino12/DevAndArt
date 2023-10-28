package com.example.devandart.data.remote

import android.util.Log
import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultsDailyRankRecommended
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.data.remote.response.ResultsRecommended
import com.example.devandart.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArtworkRepository(private val apiService: ApiService) {
    suspend fun authCookies(cookie: String, userAgent: String): Flow<AuthCookiesResponse> {
        return flow {
            try {
                val client = apiService.authCookie(cookie = cookie, userAgent = userAgent)
                if (client.error == true) {
                    throw Error("authenticated failed")
                } else {
                    emit(client)
                }
            } catch (e: Exception) {
                Log.e("authCookie", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAllIllustrations(): Flow<List<ResultsItemIllustration>> {
        return flow {
            try {
                val client = apiService.getAllIllustrations()
                val dataArray = client.body
//                if (dataArray.isNullOrEmpty()) {
//                    throw Error("${dataArray.size} is empty illustrations" )
//                }
                Log.e("dataArrayIllustration", dataArray.size.toString())
                emit(dataArray)

            } catch (e: Exception) {
                Log.e("getAllIllustrations", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRecommendedIllustrations(): Flow<List<ResultsRecommended>> {
        return flow {
            try {
                val client = apiService.getRecommendedIllustrations()
                val dataArray = client.body
                Log.e("dataArrayRecommended", dataArray?.size.toString())
                if (dataArray.isNullOrEmpty()) {
                    throw Error("${dataArray?.size} is empty recommended" )
                }
                emit(dataArray)

            } catch (e: Exception) {
                Log.e("getRecommendedIllustrations", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDailyRank(): Flow<List<ResultsDailyRankRecommended>> {
        return flow {
            try {
                val client = apiService.getDailyRankingHandler()
                val dataArray = client.body
                if (dataArray.isNullOrEmpty()) {
                    throw Error("${dataArray?.size} is empty dailyRank" )
                }
                Log.e("dataArrayDailyRank", dataArray.size.toString())
                emit(dataArray)

            } catch (e: Exception) {
                Log.e("getDailyRank", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getIllustrationDetail(id: String): Flow<ResultIllustrationDetail> {
        return flow {
            try {
                val client = apiService.getIllustrationDetail(id)
                val body = client.body
                Log.i("repoDetail", body.toString())
                if (body.illustrationId == null) {
                    throw Error("${body.illustrationId} is empty getIllustrationDetail" )
                }
                emit(body)
            } catch (e: Exception) {
                Log.e("getDetail", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        @Volatile
        private var instance: ArtworkRepository? = null

        fun getInstance(apiService: ApiService): ArtworkRepository =
            instance ?: synchronized(this) {
                instance ?: ArtworkRepository(apiService)
            }.also { instance = it }
    }
}