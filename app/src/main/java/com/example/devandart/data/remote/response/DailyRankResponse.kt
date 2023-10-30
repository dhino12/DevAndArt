package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class DailyRankResponse(
    @field:SerializedName("body")
    val body: List<ResultsDailyRankRecommended>? = null,
)

data class ResultsDailyRankRecommended (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("like")
    val like: String? = null,

    @field:SerializedName("bookmarkCount")
    val bookmarkCount: String? = null,

    @field:SerializedName("url")
    val thumbnail: List<ImageDaily>? = null,

    @field:SerializedName("createdDate")
    val createdAt: String? = null,
)

data class ImageDaily(
    @field:SerializedName("regular")
    val regular: String? = null,
)