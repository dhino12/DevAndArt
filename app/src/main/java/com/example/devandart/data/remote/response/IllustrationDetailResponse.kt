package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class IllustrationDetailResponse(
    @field:SerializedName("body")
    val body:ResultIllustrationDetail
)

data class ResultIllustrationDetail (
    @field:SerializedName("AI")
    val aiGenerated: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("createDate")
    val createDate: String? = null,

    @field:SerializedName("illustID")
    val illustrationId: String? = null,

    @field:SerializedName("like")
    val like: String? = null,

    @field:SerializedName("bookmark")
    val bookmark: String? = null,

    @field:SerializedName("view")
    val view: String? = null,

    @field:SerializedName("user")
    val user: UserProfile? = null,

    @field:SerializedName("tags")
    val tagsBody: TagBody? = null,

    @field:SerializedName("urls")
    val urls: List<UrlImages>? = null,
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