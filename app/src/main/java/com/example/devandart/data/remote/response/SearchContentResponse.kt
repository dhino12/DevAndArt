package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchContentResponse (
    @field:SerializedName("body")
    val body: ResultSearchContent
)

data class ResultSearchContent(
    @field:SerializedName("illustManga")
    val illustManga: ResultSearchItem,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("lastPage")
    val lastPage: Int
)

data class ResultSearchItem(
    @field:SerializedName("data")
    val data: List<ResultItemIllustration>
)