package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName

data class CommentByIllustrationResponse(
    @field:SerializedName("body")
    val body: ResultCommentByIllustration? = null,
)

data class ResultCommentByIllustration(
    @field:SerializedName("comments")
    val comments: List<ResultCommentItem>? = null,
)

data class ResultCommentItem(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("userName")
    val username: String? = null,

    @field:SerializedName("img")
    val thumb: String? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("stampId")
    val stampId: String? = null,

    @field:SerializedName("commentDate")
    val commentDate: String? = null,

    @field:SerializedName("commentUserId")
    val commentUserId: String? = null,

    @field:SerializedName("hasReplies")
    val hasReplies: String? = null,
)