package com.riveong.storyapp.Data.Model

import androidx.lifecycle.ViewModel
import com.riveong.storyapp.Data.Repository.StoryRepository

class StoriesViewModel(private val storyRepository: StoryRepository): ViewModel() {

    fun getStory() = storyRepository.getStory()


}