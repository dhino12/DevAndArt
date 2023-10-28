package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendedResponse(
    @field:SerializedName("body")
    val body: List<ResultsRecommended>,
)

data class ResultsRecommended (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("like")
    val like: String? = null,

    @field:SerializedName("bookmarkCount")
    val bookmarkCount: String? = null,

    @field:SerializedName("url")
    val thumbnail: String? = null,

    @field:SerializedName("createdDate")
    val createdAt: String? = null,
)