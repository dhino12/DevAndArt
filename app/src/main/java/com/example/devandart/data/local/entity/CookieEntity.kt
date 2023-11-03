package com.example.devandart.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cookieManagement")
data class CookieEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey(autoGenerate = true)
    val id: Int,

    @field:ColumnInfo(name = "cookie")
    val cookie:String = "",

    @field:ColumnInfo(name = "csrf_token")
    val csrf_token:String = ""
)