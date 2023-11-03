package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class SuggestTagResponse (
    @field:SerializedName("body")
    val body: ResultSuggestTagItem? = null,
)

data class ResultSuggestTagItem(
    @field:SerializedName("recommendTags")
    val recommendTags: ResultRecommendTag? = null,

    @field:SerializedName("thumbnails")
    val thumbnails: List<ResultItemIllustration>? = null,

    @field:SerializedName("tagTranslation")
    val tagTranslation: Map<String, ResultTagTranslation>? = null,
)

data class ResultTagTranslation(
    @field:SerializedName("en")
    val en: String? = null,

    @field:SerializedName("ko")
    val ko: String? = null,

    @field:SerializedName("romaji")
    val romaji: String? = null,

    @field:SerializedName("zh_tw")
    val zh_tw: String? = null,
)

data class ResultRecommendTag(
    @field:SerializedName("illust")
    val illust: List<ResultRecommendTagIllusts>? = null,
)

data class ResultRecommendTagIllusts (
    @field:SerializedName("ids")
    val ids: List<String>? = null,

    @field:SerializedName("tag")
    val tag: String? = null
)