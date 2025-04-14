package com.roger.skywatch

import com.roger.skywatch.data.model.PlaylistItem
import com.roger.skywatch.viewmodel.PlaylistViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fakeRepository: FakeYoutubeRepository
    private lateinit var viewModel: PlaylistViewModel

    @Before
    fun setup() {
        fakeRepository = FakeYoutubeRepository()
        viewModel = PlaylistViewModel(fakeRepository)
    }

    // 載入初始播放清單資料應有資料返回
    @Test
    fun playlistViewModel_LoadInitialPlaylist_SuccessPath() = runTest {
        viewModel.loadInitialPlaylist()
        advanceUntilIdle()
        val items: List<PlaylistItem> = viewModel.playlistItems.value
        assertTrue("Playlist should not be empty", items.isNotEmpty())
    }

    // 載入更多播放清單資料應追加新項目
    @Test
    fun playlistViewModel_LoadMorePlaylist_AppendsData() = runTest {
        viewModel.loadInitialPlaylist()
        advanceUntilIdle()
        val initialSize = viewModel.playlistItems.value.size
        viewModel.loadMorePlaylist()
        advanceUntilIdle()
        val newSize = viewModel.playlistItems.value.size
        assertTrue("More items should be appended", newSize > initialSize)
    }
}