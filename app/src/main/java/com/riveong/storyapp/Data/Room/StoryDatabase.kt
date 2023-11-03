package com.riveong.storyapp.Data.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.riveong.storyapp.Data.Repository.MapEntity
import com.riveong.storyapp.Data.Repository.StoryEntity

@Database(entities = [StoryEntity::class, MapEntity::class], version = 1, exportSchema = false)
abstract class StoryDatabase : RoomDatabase() {
    abstract fun StoryDao(): StoryDao
    abstract fun MapDao(): MapDao

    companion object {
        @Volatile
        private var instance: StoryDatabase? = null
        fun getInstance(context: Context): StoryDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    StoryDatabase::class.java, "Story.db"
                ).build()
            }
    }
}