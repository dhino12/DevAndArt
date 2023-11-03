package com.example.devandart.data.remote.retrofit

import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.data.remote.response.CommentByIllustrationResponse
import com.example.devandart.data.remote.response.FavoriteDeleteRequest
import com.example.devandart.data.remote.response.FavoriteRequest
import com.example.devandart.data.remote.response.FavoriteResponse
import com.example.devandart.data.remote.response.FavoriteSetResponse
import com.example.devandart.data.remote.response.IllustrationDetailResponse
import com.example.devandart.data.remote.response.IllustrationsResponse
import com.example.devandart.data.remote.response.MangasResponse
import com.example.devandart.data.remote.response.RelatedResponse
import com.example.devandart.data.remote.response.SearchContentResponse
import com.example.devandart.data.remote.response.SuggestTagResponse
import com.example.devandart.data.remote.response.UserProfileResponse
import com.example.devandart.utils.toInt
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("/auth")
    suspend fun authCookie(
        @Field("cookies") cookie:String,
        @Field("userAgent") userAgent:String,
    ): AuthCookiesResponse

    @GET("/ajax/top/illust?mode=all&lang=en&version=64ae9271bc3b04801a85f589f041360b20429a9d")
    suspend fun getAllIllustrations(): IllustrationsResponse
    @GET("/ajax/illust/{key}?lang=en&version=64ae9271bc3b04801a85f589f041360b20429a9d")
    suspend fun getIllustrationDetail(@Path("key") key: String): IllustrationDetailResponse
    @GET("/ajax/illusts/comments/roots")
    suspend fun getCommentsByIllustrationId(
        @Query("illust_id") illustId: String,
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 3,
    ): CommentByIllustrationResponse
    @GET("/ajax/illust/{key}/recommend/init")
    suspend fun getRelatedArtworkByIdIllust(
        @Path("key") id: String,
        @Query("limit") limit: Int = 18,
        @Query("lang") lang: String = "en",
        @Query("version") version: String = "64ae9271bc3b04801a85f589f041360b20429a9d",
    ): RelatedResponse
    @GET("/ajax/user/{key}")
    suspend fun getUserById(
        @Path("key") key: String,
        @Query("full") full: Int = true.toInt(),
    ): UserProfileResponse
    @GET("/ajax/top/manga?mode=all&lang=en&version=64ae9271bc3b04801a85f589f041360b20429a9d")
    suspend fun getAllMangas(): MangasResponse
    @GET("/ajax/search/suggestion?mode=all&lang=en&version=64ae9271bc3b04801a85f589f041360b20429a9d")
    suspend fun getAllTagRecommend(): SuggestTagResponse
    @GET("/ajax/search/artworks/{keyword}")
    suspend fun getSearchByKeyword(
        @Path("keyword") keyword: String,
        @Query("word") word: String,
        @Query("order") order: String = "date_d",
        @Query("mode") mode: String = "all",
        @Query("p") page: String = "1",
        @Query("type") type: String = "all"
    ): SearchContentResponse
    @GET("/en")
    suspend fun getHTML(): Response<ResponseBody>
    @POST("/ajax/illusts/bookmarks/add")
    suspend fun setBookmark(@Body postBody: FavoriteRequest): FavoriteSetResponse
    @GET("/ajax/user/{userId}/illusts/bookmarks")
    suspend fun getBookmark(
        @Path("userId") userId: String,
        @Query("tag") tag:String = "",
        @Query("offset") offset:String = "0",
        @Query("limit") limit:String = "48",
        @Query("rest") rest:String = "show",
        @Query("lang") lang:String = "en",
        @Query("version") version: String = "f918559392a08c108e1834ce490dce6414796b97",
    ): FavoriteResponse
    @FormUrlEncoded
    @POST("/ajax/illusts/bookmarks/delete")
    suspend fun deleteBookmark(@Field("bookmark_id") bookmark_id: String): FavoriteDeleteRequest
}