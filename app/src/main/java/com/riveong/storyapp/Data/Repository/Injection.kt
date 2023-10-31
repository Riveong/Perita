package com.riveong.storyapp.Data.Repository

import android.content.Context
import com.riveong.storyapp.Data.Preferences.SettingPreferences
import com.riveong.storyapp.Data.Preferences.dataStore
import com.riveong.storyapp.Data.Retrofit.ApiConfig
import com.riveong.storyapp.Data.Room.StoryDatabase
import com.riveong.storyapp.Data.utils.AppExecutors
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val pref = SettingPreferences.getInstance(context.dataStore)
        val jwt = runBlocking { pref.getJwtToken().first() }
        val apiService = ApiConfig.getApiService(jwt)
        val database = StoryDatabase.getInstance(context)
        val dao = database.StoryDao()
        val appExecutors = AppExecutors()
        return StoryRepository.getInstance(apiService, dao, appExecutors, jwt.toString())
    }
}