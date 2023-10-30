package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserProfileResponse(
    @field:SerializedName("body")
    val body: ResultUserProfile,
)

data class ResultUserProfile(
    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("premium")
    val premium: Boolean? = null,

    @field:SerializedName("isFollowed")
    val isFollowed: Boolean? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("commentHtml")
    val commentHtml: String? = null,

    @field:SerializedName("webpage")
    val webpage: String? = null,

    @field:SerializedName("region")
    val region: Region? = null,

    @field:SerializedName("age")
    val age: String? = null,

    @field:SerializedName("birthday")
    val birthday: String? = null,

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("job")
    val job: String? = null,

    @field:SerializedName("workspace")
    val workspace: UserWorkspace? = null,

    @field:SerializedName("social")
    val social: List<Social>? = null,
)
// 91932519
data class Social(
    @field:SerializedName("twitter")
    val twitter: String? = null,

    @field:SerializedName("instagram")
    val instagram: String? = null,

    @field:SerializedName("facebook")
    val facebook: String? = null,

    @field:SerializedName("youtube")
    val youtube: String? = null,
)
data class UserWorkspace(
    @field:SerializedName("userWorkspacePc")
    val userWorkspacePc: String? = null,
)
data class Region(
    @field:SerializedName("name")
    val nameRegion: String? = null,
    @field:SerializedName("region")
    val region: String? = null,
    @field:SerializedName("prefecture")
    val prefecture: String? = null,
)