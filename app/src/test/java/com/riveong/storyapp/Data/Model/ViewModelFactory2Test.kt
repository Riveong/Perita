package com.riveong.storyapp.Data.Model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.riveong.storyapp.Data.Repository.StoryRepository
import com.riveong.storyapp.Data.Retrofit.ListStoryItem
import com.riveong.storyapp.ui.Adapter.StoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelFactory2Test{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRule = MainDispantcher()
    @Mock
    private lateinit var listStoryRepository: StoryRepository

    @Test
    fun `Ketika ada cerita dan sukses`() = runTest {

        val dummyListStoryItem = DataDummy.generateDummyListStoryItem()
        val data: PagingData<ListStoryItem> = StoriesPagingSource.snapshot(dummyListStoryItem)
        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data
        Mockito.`when`(listStoryRepository.getPeg()).thenReturn(expectedStory)
        val listStoryViewModel = StoriesViewModel(listStoryRepository)

        val actualStory: PagingData<ListStoryItem> = listStoryViewModel.pagging().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = nooplistUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)

        assertNotNull(differ.snapshot())
        assertEquals(dummyListStoryItem.size, differ.snapshot().size)
        assertEquals(dummyListStoryItem[0], differ.snapshot()[0])
    }

    @Test
    fun `Ketika Tidak ada cerita`() = runTest {

        val emptyListStoryItem = emptyList<ListStoryItem>()
        val data: PagingData<ListStoryItem> = StoriesPagingSource.snapshot(emptyListStoryItem)

        val expectedStory = MutableLiveData<PagingData<ListStoryItem>>()
        expectedStory.value = data

        Mockito.`when`(listStoryRepository.getPeg()).thenReturn(expectedStory)

            val listStoryViewModel = StoriesViewModel(listStoryRepository)


        val actualStory: PagingData<ListStoryItem> = listStoryViewModel.pagging().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = nooplistUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualStory)


        assertEquals(0, differ.snapshot().size)
    }

    companion object {

        val nooplistUpdateCallback = object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {}

            override fun onRemoved(position: Int, count: Int) {}

            override fun onMoved(fromPosition: Int, toPosition: Int) {}

            override fun onChanged(position: Int, count: Int, payload: Any?) {}
        }
    }
}

class StoriesPagingSource: PagingSource<Int, LiveData<List<ListStoryItem>>>() {

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListStoryItem>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListStoryItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

    companion object {
        fun snapshot(items: List<ListStoryItem>): PagingData<ListStoryItem> {
            return PagingData.from(items)
        }
    }
}