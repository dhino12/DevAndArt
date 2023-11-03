package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class FavoriteResponse (
    @field:SerializedName("body")
    val body:ResultItemFavorite,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message:String
)

data class ResultItemFavorite(
    @field:SerializedName("last_bookmark_id")
    val lastBookmarkId:String? = "",

    @field:SerializedName("stacc_status_id")
    val staccStatusId:String? = ""
)