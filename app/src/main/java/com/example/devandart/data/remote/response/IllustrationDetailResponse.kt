package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class IllustrationDetailResponse(
    @field:SerializedName("body")
    val body: ResultIllustrationDetail
)

data class ResultIllustrationDetail (
    @field:SerializedName("illustId")
    val illustId: String? = null,

    @field:SerializedName("illustTitle")
    val illustTitle: String? = null,

    @field:SerializedName("illustComment")
    val illustComment: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("createDate")
    val createDate: String? = null,

    @field:SerializedName("urls")
    val urls: UrlImages? = null,

    @field:SerializedName("tags")
    val tagsBody: TagBody? = null,

    @field:SerializedName("alt")
    val alt: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("userName")
    val username: String? = null,

    @field:SerializedName("userAccount")
    val userAccount: String? = null,

    @field:SerializedName("pageCount")
    val pageCount: Int? = null,

    @field:SerializedName("commentCount")
    val commentCount: Int? = null,

    @field:SerializedName("viewCount")
    val viewCount: Int? = null,

    @field:SerializedName("likeCount")
    val likeCount: Int? = null,

    @field:SerializedName("bookmarkCount")
    val bookmarkCount: Int? = null,

    @field:SerializedName("bookmarkData")
    val bookmarkData: BookmarkData? = null,

    @field:SerializedName("userIllusts")
    val userIllusts: Map<String, UserIllusts>? = null,
)

data class UserIllusts(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("tags")
    val tags: List<String>? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("userName")
    val username: String? = null,

    @field:SerializedName("pageCount")
    val pageCount: Int? = null,

    @field:SerializedName("alt")
    val alt: String? = null,

    @field:SerializedName("createDate")
    val createDate: String? = null,

    @field:SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,
)

data class UrlImages (
    @field:SerializedName("mini")
    val mini: String? = null,
    
    @field:SerializedName("original")
    val original: String? = null,
    
    @field:SerializedName("regular")
    val regular: String? = null,
    
    @field:SerializedName("thumb")
    val thumb: String? = null,

    @field:SerializedName("small")
    val small: String? = null,
)

data class UserProfile(
    @field:SerializedName("id")
    val idUser: String? = null,

    @field:SerializedName("name")
    val nameUser: String? = null,
)

data class TagBody(
    @field:SerializedName("tags")
    val tags: List<TagContent>? = null,
)

data class TagContent(
    @field:SerializedName("tag")
    val tag: String? = null,

    @field:SerializedName("romaji")
    val romaji: String? = null,
)