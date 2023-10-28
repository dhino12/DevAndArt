package com.example.devandart.data.remote.retrofit

import com.example.devandart.data.remote.response.AuthCookiesResponse
import com.example.devandart.data.remote.response.DailyRankResponse
import com.example.devandart.data.remote.response.IllustrationsResponse
import com.example.devandart.data.remote.response.RecommendedResponse
import com.example.devandart.data.remote.response.ResultIllustrationDetailResponse
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

    @GET("/top/illust")
    suspend fun getRecommendedIllustrations(): RecommendedResponse

    @GET("/top/daily")
    suspend fun getDailyRankingHandler(): DailyRankResponse

    @GET("/illust/{key}")
    suspend fun getIllustrationDetail(@Path("key") key: String): ResultIllustrationDetailResponse
}