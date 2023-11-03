package com.example.devandart.data.remote.response

import com.google.gson.JsonElement
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

    @field:SerializedName("imageBig")
    val imageBig: String? = null,

    @field:SerializedName("background")
    val background: BackgroundData? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("premium")
    val premium: Boolean? = null,

    @field:SerializedName("isFollowed")
    val isFollowed: Boolean? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("commentHtml")
    val commentHtml: String? = null,

    @field:SerializedName("webpage")
    val webpage: String? = null,

    @field:SerializedName("region")
    val region: Region? = null,

    @field:SerializedName("age")
    val age: Age? = null,

    @field:SerializedName("birthday")
    val birthday: Birthday? = null,

    @field:SerializedName("gender")
    val gender: Gender? = null,

    @field:SerializedName("job")
    val job: JobData? = null,

    @field:SerializedName("workspace")
    val workspace: UserWorkspace? = null,

    @field:SerializedName("social")
    val social: JsonElement? = null,
)

data class BackgroundData(
    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("color")
    val color: String? = null,
)

data class Age(
    @field:SerializedName("name")
    val name: String? = null,
)

data class Birthday(
    @field:SerializedName("name")
    val name: String? = null,
)

data class Gender(
    @field:SerializedName("gender")
    val name: String? = null,
)

data class JobData(
    @field:SerializedName("name")
    val name: String? = null,
)
// 91932519
data class Social(
    @field:SerializedName("twitter")
    val twitter: UrlLink? = null,

    @field:SerializedName("instagram")
    val instagram: UrlLink? = null,

    @field:SerializedName("facebook")
    val facebook: UrlLink? = null,

    @field:SerializedName("youtube")
    val youtube: UrlLink? = null,
)

data class UrlLink(
    @field:SerializedName("url")
    val url: String? = null,
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