package com.example.devandart.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.devandart.data.local.entity.CookieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao {
    @Query("SELECT * FROM cookieManagement")
    suspend fun getCookie():CookieEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCookie(cookie: CookieEntity)
}