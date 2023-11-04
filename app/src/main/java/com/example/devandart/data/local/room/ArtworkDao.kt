package com.example.devandart.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.devandart.data.local.entity.CookieEntity
import com.example.devandart.data.local.entity.UserEntity
import retrofit2.http.DELETE

@Dao
interface ArtworkDao {
    @Query("SELECT * FROM cookieManagement")
    suspend fun getCookie():CookieEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveCookie(cookie: CookieEntity)
    @Update
    suspend fun updateCookie(cookie: CookieEntity)
    @Delete
    suspend fun deleteCookie(cookie: CookieEntity)

    @Query("SELECT * FROM userManagement")
    suspend fun getUser(): UserEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveUser(user: UserEntity)
    @Delete
    suspend fun deleteUser(cookie: UserEntity)
}