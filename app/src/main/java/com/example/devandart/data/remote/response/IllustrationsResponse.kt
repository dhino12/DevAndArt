package com.example.devandart.data.remote.response

import com.google.gson.annotations.SerializedName
/**
 * parent root
 */
data class IllustrationsResponse(
    @field:SerializedName("body")
    val body: ResultsItemIllustration
)

/**
 * child no1 (LEVEL 1)
 */
data class ResultsItemIllustration(
    @field:SerializedName("thumbnails")
    val thumbnails: ResultsItemThumbnails? = null,

    @field:SerializedName("page")
    val page: ResultItemPage? = null,
)

/**
 * child no2 (LEVEL 2)
 */
data class ResultItemPage(
    @field:SerializedName("recommend")
    val recommend: ResultItemPageRecommend? = null,

    @field:SerializedName("recommendByTag")
    val recommendByTag: List<ResultItemPageRecommendByTag>? = null,

    @field:SerializedName("ranking")
    val rankings: ResultItemPageRanking? = null,
)

/**
 * child no3 (LEVEL 3)
 */

data class ResultItemPageRecommend(
    @field:SerializedName("ids")
    val idIllustrations: List<String>? = null,
)

data class ResultItemPageRecommendByTag(
    @field:SerializedName("tag")
    val tag: String? = null,

    @field:SerializedName("ids")
    val idIllustrations: List<String>? = null,
)

data class ResultItemPageRanking(
    @field:SerializedName("items")
    val items: List<ResultItemPageRankingItems>? = null,

    @field:SerializedName("date")
    val date: String? = null,
)

/**
 * child no4 (LEVEL 4)
 */

data class ResultItemPageRankingItems(
    @field:SerializedName("rank")
    val rank: String? = null,

    @field:SerializedName("id")
    val id: String? = null,
)

data class ResultsItemThumbnails(
    @field:SerializedName("illust")
    val illusts: List<ResultItemIllustration>? = null,

    @field:SerializedName("novel")
    val novels: List<ResultItemContensNovel>? = null,
)

data class ResultItemContensNovel(
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

    @field:SerializedName("textCount")
    val textCount: Int? = null,

    @field:SerializedName("wordCount")
    val wordCount: String? = null,

    @field:SerializedName("readingTime")
    val readingTime: String? = null,

    @field:SerializedName("createDate")
    val createDate: String? = null,

    @field:SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,

    @field:SerializedName("bookmarkCount")
    val bookmarkCount: Int? = null,

    @field:SerializedName("bookmarkData")
    val bookmarkData: BookmarkData? = null,

    @field:SerializedName("seriesId")
    val seriesId: String? = null,

    @field:SerializedName("seriesTitle")
    val seriesTitle: String? = null,
)

data class ResultItemIllustration(
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

    @field:SerializedName("bookmarkData")
    var bookmarkData: BookmarkData? = null,

    @field:SerializedName("alt")
    val alt: String? = null,

    @field:SerializedName("createDate")
    val createDate: String? = null,

    @field:SerializedName("profileImageUrl")
    val profileImageUrl: String? = null,
)

data class BookmarkData(
    @field:SerializedName("id")
    var id: String? = null,
    @field:SerializedName("private")
    val private: String? = null,
)