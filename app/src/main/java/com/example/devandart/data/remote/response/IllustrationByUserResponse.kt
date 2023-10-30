package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class IllustrationByUserResponse(
    @field:SerializedName("body")
    val body:ResultItemIllustrationByUser
)

data class ResultItemIllustrationByUser(
    @field:SerializedName("illusts")
    val illusts:List<ResultIllustrationByUser>
)

data class ResultIllustrationByUser(
    @field:SerializedName("id")
    val id:String,

    @field:SerializedName("title")
    val title:String,

    @field:SerializedName("url")
    val thumb:String,

    @field:SerializedName("tags")
    val tags:List<String>,

    @field:SerializedName("description")
    val description:String,

    @field:SerializedName("alt")
    val alt:String,

    @field:SerializedName("createdDate")
    val createdDate:String,

    @field:SerializedName("profileImageUrl")
    val profileImageUrl:String,

    @field:SerializedName("username")
    val username:String,

    @field:SerializedName("userId")
    val userId:String,
)