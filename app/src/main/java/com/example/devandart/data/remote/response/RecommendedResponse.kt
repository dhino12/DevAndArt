package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class RelatedResponse(
    @field:SerializedName("body")
    val body: ResultsRelatedResponse,
)

data class ResultsRelatedResponse (
    @field:SerializedName("illusts")
    val illusts: List<ResultIllustRelated>? = null,

    @field:SerializedName("nextIds")
    val nextIds: List<String>? = null,
)

data class ResultIllustRelated (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val thumbnail: String? = null,

    @field:SerializedName("createdDate")
    val createdAt: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("userName")
    val username: String? = null,

    @field:SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,

    @field:SerializedName("type")
    val type: String? = null,
)

data class ImagesRecommended(
    @field:SerializedName("regular")
    val regular: String? = null,
)