package com.riveong.storyapp.Data.Repository

import android.service.autofill.UserData
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.liveData
import com.google.gson.internal.GsonBuildConfig
import com.riveong.storyapp.Data.Retrofit.ApiService
import com.riveong.storyapp.Data.Retrofit.ListStoryItem
import com.riveong.storyapp.Data.Retrofit.ListStoryResponse
import com.riveong.storyapp.Data.Room.StoryDao
import com.riveong.storyapp.Data.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository private constructor(
    
    private val apiService: ApiService,
    private val storyDao: StoryDao,
    private val appExecutors: AppExecutors,
    private val jwt: String
) {
    private val result = MediatorLiveData<Result<List<StoryEntity>>>()

    fun getStory(): LiveData<Result<List<StoryEntity>>> {
        result.value = Result.Loading
        val client = apiService.getStories()
        client.enqueue(object : Callback<ListStoryResponse> {
            override fun onResponse(call: Call<ListStoryResponse>, response: Response<ListStoryResponse>) {
                if (response.isSuccessful) {
                    val stories = response.body()?.listStory
                    val storiesList = ArrayList<StoryEntity>()
                    appExecutors.diskIO.execute {
                        stories?.forEach { story ->
                            val stories = StoryEntity(
                                title=story!!.name,
                                Description = story.description,
                                publishedAt = story?.createdAt,
                                urlToImage = story?.photoUrl,
                                url = story.id!!
                            )
                            storiesList.add(stories)
                        }
                        storyDao.deleteAll()
                        storyDao.insertStories(storiesList)
                    }
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = storyDao.getStories()
        result.addSource(localData) { newData: List<StoryEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }



    companion object {
        @Volatile
        private var instance: StoryRepository? = null
        fun getInstance(
            apiService: ApiService,
            storyDao: StoryDao,
            appExecutors: AppExecutors,
            jwt: String
        ): StoryRepository =
            instance ?: synchronized(this) {
                instance ?: StoryRepository(apiService, storyDao, appExecutors, jwt)
            }.also { instance = it }
    }
}