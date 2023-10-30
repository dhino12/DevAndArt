package com.example.devandart.data.remote.retrofit

import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.data.remote.response.CommentByIllustrationResponse
import com.example.devandart.data.remote.response.DailyRankResponse
import com.example.devandart.data.remote.response.IllustrationByUserResponse
import com.example.devandart.data.remote.response.IllustrationDetailResponse
import com.example.devandart.data.remote.response.IllustrationsResponse
import com.example.devandart.data.remote.response.RecommendedResponse
import com.example.devandart.data.remote.response.UserProfileResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("/auth")
    suspend fun authCookie(
        @Field("cookies") cookie:String,
        @Field("userAgent") userAgent:String,
    ): AuthCookiesResponse

    @GET("/illust")
    suspend fun getAllIllustrations(): IllustrationsResponse
    @GET("/illust/{key}")
    suspend fun getIllustrationDetail(@Path("key") key: String): IllustrationDetailResponse
    @GET("/illust/comments/{key}")
    suspend fun getCommentsByIllustrationId(@Path("key") key: String): CommentByIllustrationResponse
    @GET("/top/illust")
    suspend fun getRecommendedIllustrations(): RecommendedResponse
    @GET("/top/daily")
    suspend fun getDailyRankingHandler(): DailyRankResponse
    @GET("/user/{key}")
    suspend fun getUserById(@Path("key") key: String): UserProfileResponse
    @GET("/user/illust/{key}")
    suspend fun getIllustrationByUserId(@Path("key") key: String): IllustrationByUserResponse
}