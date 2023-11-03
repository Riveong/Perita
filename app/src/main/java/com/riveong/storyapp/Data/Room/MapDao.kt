package com.riveong.storyapp.Data.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.riveong.storyapp.Data.Repository.MapEntity

@Dao
interface MapDao {
    @Query("SELECT * FROM map ORDER BY publishedAt DESC")
    fun getStories(): LiveData<List<MapEntity>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStories(news: List<MapEntity>)

    @Update
    fun updateStories(news: MapEntity)

    @Query("DELETE FROM map")
    fun deleteAll()

}