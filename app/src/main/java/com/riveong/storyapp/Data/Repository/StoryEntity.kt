package com.riveong.storyapp.Data.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
class StoryEntity(
    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "Description")
    val Description: String?,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String?,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "url")
    @field:PrimaryKey
    val url: String,

)