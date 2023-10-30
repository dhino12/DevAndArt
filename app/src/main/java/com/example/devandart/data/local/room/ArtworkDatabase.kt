package com.example.devandart.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.devandart.data.local.entity.CookieEntity

@Database(
    entities = [CookieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ArtworkDatabase: RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao

    companion object {
        private var Instance: ArtworkDatabase? = null
        fun getDatabase(context: Context): ArtworkDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context, ArtworkDatabase::class.java, "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        Instance = it
                    }
            }
        }
    }
}