package com.riveong.storyapp.Data.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.riveong.storyapp.Data.Repository.StoryEntity

@Dao
interface StoryDao {
    @Query("SELECT * FROM story ORDER BY publishedAt DESC")
    fun getStories(): LiveData<List<StoryEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStories(news: List<StoryEntity>)

    @Update
    fun updateStories(news: StoryEntity)

    @Query("DELETE FROM story")
    fun deleteAll()

}