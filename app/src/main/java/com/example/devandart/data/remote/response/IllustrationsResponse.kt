package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class IllustrationsResponse(
    @field:SerializedName("body")
    val body: List<ResultsItemIllustration>
)

data class ResultsItemIllustration(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val thumbnail: String? = null,

    @field:SerializedName("tags")
    val tags: List<String>? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    @field:SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,

    @field:SerializedName("alt")
    val alt: String? = null,

    @field:SerializedName("createDate")
    val createDate: String? = null,
)