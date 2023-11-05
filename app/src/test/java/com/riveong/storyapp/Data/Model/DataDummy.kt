package com.riveong.storyapp.Data.Model

import com.riveong.storyapp.Data.Retrofit.ListStoryItem

object DataDummy {
    fun generateDummyListStoryItem(): List<ListStoryItem> {
        val listStoryItem = ArrayList<ListStoryItem>()
        for (i in 1..30) {
            val storyList = ListStoryItem(
                "UrlPhoto$i",
                "Published$i",
                "Name$i",
                "Description$i",
                i.toDouble(),
                "id: $i",
                 i.toDouble()
            )
            listStoryItem.add(storyList)
        }
        return listStoryItem
    }
}