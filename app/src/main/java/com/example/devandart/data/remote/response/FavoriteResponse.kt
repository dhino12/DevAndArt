package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class FavoriteResponse (
    @field:SerializedName("body")
    val body:ResultItemFavoriteData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message:String
)

data class ResultItemFavoriteData(
    @field:SerializedName("works")
    val works:List<ResultItemIllustration> = listOf(),

    @field:SerializedName("total")
    val total: Int = 0,

    @field:SerializedName("bookmarkTags")
    val bookmarkTags:List<String> = listOf()
)