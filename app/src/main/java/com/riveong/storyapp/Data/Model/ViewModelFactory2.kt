package com.riveong.storyapp.Data.Model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riveong.storyapp.Data.Repository.Injection
import com.riveong.storyapp.Data.Repository.StoryRepository

class ViewModelFactory2 private constructor(private val storiesRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StoriesViewModel::class.java)) {
            return StoriesViewModel(storiesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory2? = null
        fun getInstance(context: Context): ViewModelFactory2 =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory2(Injection.provideRepository(context))
            }.also { instance = it }
    }
}