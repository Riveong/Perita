package com.riveong.storyapp.Data.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.riveong.storyapp.Data.Repository.StoryRepository
import com.riveong.storyapp.Data.Retrofit.ListStoryItem

class StoriesViewModel(private val storyRepository: StoryRepository): ViewModel() {


    fun getMap() = storyRepository.getMap()

    fun pagging(): LiveData<PagingData<ListStoryItem>> =
        storyRepository.getPeg().cachedIn(viewModelScope)


}