package com.example.devandart.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userManagement")
data class UserEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id:String,

    @field:ColumnInfo(name = "pixivId")
    val pixivId:String,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "profileImg")
    val profileImg: String,

    @field:ColumnInfo(name = "profileImgBig")
    val profileImgBig: String,

    @field:ColumnInfo(name = "premium")
    val premium: Boolean,

    @field:ColumnInfo(name = "adult")
    val adult: Boolean,
)