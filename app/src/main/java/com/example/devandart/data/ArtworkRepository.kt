package com.example.devandart.data

import android.util.Log
import androidx.compose.runtime.collectAsState
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.data.local.room.ArtworkDao
import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.data.remote.response.ResultCommentByIllustration
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultItemIllustrationByUser
import com.example.devandart.data.remote.response.ResultUserProfile
import com.example.devandart.data.remote.response.ResultsDailyRankRecommended
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.data.remote.response.ResultsRecommended
import com.example.devandart.data.remote.retrofit.ApiService
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ArtworkRepository(
    private val apiService: ApiService,
    private val artworkDao: ArtworkDao
) {
    suspend fun authCookies(cookie: String, userAgent: String): Flow<UiState<AuthCookiesResponse>> {
        return flow {
            try {
                val client = apiService.authCookie(cookie = cookie, userAgent = userAgent)
                if (client.error == true) {
                    throw Error("authenticated failed")
                } else {
                    emit(UiState.Success(client))
                }
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
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

    suspend fun getRecommendedIllustrations(): Flow<UiState<List<ResultsRecommended>>> {
        return flow {
            try {
                val client = apiService.getRecommendedIllustrations()
                val dataArray = client.body
                if (dataArray.isNullOrEmpty()) {
                    throw Error("${dataArray?.size} is empty recommended" )
                }
                emit(UiState.Success(dataArray))

            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getRecommendedIllustrations", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDailyRank(): Flow<UiState<List<ResultsDailyRankRecommended>>> {
        return flow {
            try {
                emit(UiState.Loading)
                val client = apiService.getDailyRankingHandler()
                val dataArray = client.body
                if (dataArray.isNullOrEmpty()) {
                    throw Error("${dataArray?.size} is empty dailyRank" )
                }
                Log.e("dataArrayDailyRank", dataArray.size.toString())
                emit(UiState.Success(dataArray))

            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getDailyRank", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getIllustrationDetail(id: String): Flow<UiState<ResultIllustrationDetail>> {
        return flow {
            try {
                val client = apiService.getIllustrationDetail(id)
                val body = client.body
                Log.i("repoDetail", body.toString())
                if (body.illustrationId == null) {
                    throw Error("${body.illustrationId} is empty getIllustrationDetail" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getDetail", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserById(id: String): Flow<UiState<ResultUserProfile>> {
        return flow {
            try {
                val client = apiService.getUserById(id)
                val body = client.body
                Log.i("userProfile", body.toString())
                if (body.userId == null) {
                    throw Error("${body.userId} is empty getUserProfileId" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getUserProfile", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getIllustrationByUserId(id: String): Flow<UiState<ResultItemIllustrationByUser>> {
        return flow {
            try {
                val client = apiService.getIllustrationByUserId(id)
                val body = client.body
                Log.i("userProfile", body.toString())
                if (body.illusts.isEmpty()) {
                    throw Error("${body.illusts.size} is empty getIllustByUserId" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getIllustByUserId", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getCommentByIllustrationId(id: String): Flow<UiState<List<ResultCommentByIllustration>>> {
        return flow {
            try {
                val client = apiService.getCommentsByIllustrationId(id)
                val body = client.body
                Log.i("userProfile", body.toString())
                if (body?.isNullOrEmpty() === null) {
                    throw Error("${body?.size} is empty getCommentByIllustId" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getCommentByIllustId", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun saveCookie(cookie: CookieEntity) {
        artworkDao.saveCookie(cookie)
    }

    suspend fun getCookieFromDb(): Flow<UiState<CookieEntity>> {
        return flow {
            try {
                val cookie = artworkDao.getCookie()
                if (cookie.cookie.isBlank()) {
                    throw Error("cookie not found: " + cookie.cookie + " ?")
                }
                emit(UiState.Success(cookie))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        @Volatile
        private var instance: ArtworkRepository? = null

        fun getInstance(apiService: ApiService, artworkDao: ArtworkDao): ArtworkRepository =
            instance ?: synchronized(this) {
                instance ?: ArtworkRepository(apiService, artworkDao)
            }.also { instance = it }

        fun destroyInstance() {
            instance = null
        }
    }
}