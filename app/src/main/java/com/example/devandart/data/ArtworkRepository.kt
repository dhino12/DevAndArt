package com.example.devandart.data

import android.util.Log
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.data.local.entity.UserEntity
import com.example.devandart.data.local.room.ArtworkDao
import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.data.remote.response.FavoriteDeleteRequest
import com.example.devandart.data.remote.response.FavoriteRequest
import com.example.devandart.data.remote.response.ResultCommentByIllustration
import com.example.devandart.data.remote.response.ResultIllustrationDetail
import com.example.devandart.data.remote.response.ResultItemFavorite
import com.example.devandart.data.remote.response.ResultItemFavoriteData
import com.example.devandart.data.remote.response.ResultSearchContent
import com.example.devandart.data.remote.response.ResultSuggestTagItem
import com.example.devandart.data.remote.response.ResultUserProfile
import com.example.devandart.data.remote.response.ResultsItemIllustration
import com.example.devandart.data.remote.response.ResultsRelatedResponse
import com.example.devandart.data.remote.retrofit.ApiService
import com.example.devandart.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.ResponseBody

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
    suspend fun getAllIllustrations(): Flow<UiState<ResultsItemIllustration>> {
        return flow {
            try {
                val client = apiService.getAllIllustrations()
                val dataArray = client.body
//                if (dataArray.isNullOrEmpty()) {
//                    throw Error("${dataArray.size} is empty illustrations" )
//                }
//                Log.e("dataArrayIllustration", dataArray.thumbnails?.illusts?.size.toString())
                emit(UiState.Success(dataArray))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getAllIllustrations", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getIllustrationDetail(id: String): Flow<UiState<ResultIllustrationDetail>> {
        return flow {
            try {
                val client = apiService.getIllustrationDetail(id)
                val body = client.body
                Log.i("repoDetail", body.toString())
                if (body.illustId == null) {
                    throw Error("${body.illustId} is empty getIllustrationDetail" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getDetail", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getCommentByIllustrationId(id: String): Flow<UiState<ResultCommentByIllustration>> {
        return flow {
            try {
                val client = apiService.getCommentsByIllustrationId(id)
                val body = client.body
                Log.i("userProfile", body.toString())
                if (body?.comments === null) {
                    throw Error("null comment is empty getCommentByIllustId" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getCommentByIllustId", e.message.toString())
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
    suspend fun getRelatedArtwork(id: String): Flow<UiState<ResultsRelatedResponse>> {
        return flow {
            try {
                val client = apiService.getRelatedArtworkByIdIllust(id)
                val body = client.body
                Log.i("userProfile", body.toString())
                if (body.illusts == null) {
                    throw Error("${body.illusts} is empty getRelatedArtwork" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getRelatedArtwork", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAllMangas(): Flow<UiState<ResultsItemIllustration>> {
        return flow {
            try {
                val client = apiService.getAllMangas()
                val body = client.body
                Log.i("manga", body.toString())
                if (body.thumbnails == null) {
                    throw Error("${body.thumbnails} is empty getAllManga" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getAllManga", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getAllTagRecommend(): Flow<UiState<ResultSuggestTagItem>> {
        return flow {
            try {
                val client = apiService.getAllTagRecommend()
                val body = client.body
                Log.i("manga", body.toString())
                if (body?.thumbnails == null) {
                    throw Error("${body?.thumbnails} is empty getAllManga" )
                }
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("getAllManga", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getSearchByKeyword(keyword: String): Flow<UiState<ResultSearchContent>> {
        return flow {
            try {
                val client = apiService.getSearchByKeyword(keyword = keyword, word = keyword)
                val body = client.body
                Log.i("searchByKeyword", body.illustManga.data.size.toString())
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("searchByKeyword", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getHTML(): Flow<UiState<ResponseBody>> {
        return flow {
            try {
                val client = apiService.getHTML()
                val body = client.body() ?: throw Error ("body HTML is Null")
                Log.i("HTML", body.toString())
                emit(UiState.Success(body))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
                Log.e("HTML CATCH", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getFavorite(userId: String): Flow<UiState<ResultItemFavoriteData>> {
        return flow {
            try {
                val client = apiService.getBookmark(userId)
                val body = client.body
                Log.i("POST Favorite", body.toString())
                emit(UiState.Success(body))
            } catch (e: Exception) {
                Log.e("POST Favorite Null", e.message.toString())
                emit(UiState.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun setFavorite(postData: FavoriteRequest, illustId: String): Flow<UiState<ResultItemFavorite>> {
        return flow {
            try {
                Log.e("FAVORITE SET Repo", postData.toString())
                val client = apiService.setBookmark(postData)
                val body = client.body
                body.illustId = illustId
                Log.i("POST Favorite", body.toString())
                emit(UiState.Success(body))
            } catch (e: Exception) {
                Log.e("POST Favorite Null", e.message.toString())
                emit(UiState.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun deleteFavorite(idFavorite: String): UiState<FavoriteDeleteRequest> {
        return try {
            Log.e("DELETE FAV SET Repo", idFavorite.toString())
            val client = apiService.deleteBookmark(idFavorite)
            UiState.Success(client)
        } catch (e: Exception) {
            Log.e("DELETE Favorite Null", e.message.toString())
            UiState.Error(e.message.toString())
        }
    }
    /**
     * Local DB sqlite
     */
    suspend fun saveCookie(cookie: CookieEntity) {
        artworkDao.saveCookie(cookie)
    }
    suspend fun updateCookie(cookie: CookieEntity) {
        Log.e("Update cookie", cookie.csrf_token.toString())
        artworkDao.updateCookie(cookie)
    }
    suspend fun deleteCookie(cookie: CookieEntity) {
        Log.e("Update cookie", cookie.csrf_token.toString())
        artworkDao.deleteCookie(cookie)
    }
    suspend fun getCookieFromDb(): Flow<UiState<CookieEntity>> {
        return flow {
            try {
                val cookie = artworkDao.getCookie()
                Log.e("cookieDB", cookie.cookie.toString())
                if (cookie.cookie.isBlank()) {
                    throw Error("cookie not found: " + cookie.cookie + " ?")
                }
                emit(UiState.Success(cookie))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun getUserFromDb(): Flow<UiState<UserEntity>> {
        return flow {
            try {
                val cookie = artworkDao.getUser()
                Log.e("DB USER", cookie.name)
                if (cookie.pixivId.isBlank()) {
                    throw Error("cookie not found: " + cookie.pixivId + " ?")
                }
                emit(UiState.Success(cookie))
            } catch (e: Exception) {
                emit(UiState.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
    suspend fun saveUser(user: UserEntity) {
        artworkDao.saveUser(user)
    }
    suspend fun deleteUser(user: UserEntity) {
        artworkDao.deleteUser(user)
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