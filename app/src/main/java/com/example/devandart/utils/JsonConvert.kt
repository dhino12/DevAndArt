package com.example.devandart.utils

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaGlobalData(
    val token: String,
    val userData: UserData?,
    var cookie: String?,
): Parcelable

@Parcelize
data class UserData(
    val pixivId:String,
    val name: String,
    val profileImg: String,
    val profileImgBig: String,
    val premium: Boolean,
    val adult: Boolean,
): Parcelable
fun toJsonMetaData(content: String): MetaGlobalData {
    val gson = Gson()
    val metaDataGlobal: MetaGlobalData = gson.fromJson(content, MetaGlobalData::class.java)
    return metaDataGlobal
}
