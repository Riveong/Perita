package com.riveong.storyapp.Data.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map")
class MapEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "Description")
    val Description: String?,

    @field:ColumnInfo(name = "publishedAt")
    val publishedAt: String?,

    @field:ColumnInfo(name = "urlToImage")
    val urlToImage: String? = null,

    @field:ColumnInfo(name = "lat")
    val lat: Double,

    @field:ColumnInfo(name = "lon")
    val lon: Double,



    )