package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.FormUrlEncoded

data class FavoriteRequest (
    @field:SerializedName("illust_id")
    val illust_Id: String? = null,

    @field:SerializedName("restrict")
    val restrict: Int? = 0,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("tags")
    val tags: List<String>? = null,
)

data class FavoriteDeleteRequest(
    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("error")
    val error: Boolean? = false,

    @field:SerializedName("body")
    val body: List<String>? = null,
)