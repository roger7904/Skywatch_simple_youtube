package com.roger.skywatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roger.skywatch.data.model.PlaylistItem
import com.roger.skywatch.data.repository.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val repository: YoutubeRepository
) : ViewModel() {

    private val _playlistItems = MutableStateFlow<List<PlaylistItem>>(emptyList())
    val playlistItems: StateFlow<List<PlaylistItem>> = _playlistItems

    private val _nextPageToken = MutableStateFlow<String?>(null)
    val loading = MutableStateFlow(false)
    val errorMsg = MutableStateFlow<String?>(null)
    val searchQuery = MutableStateFlow("")

    init {
        loadInitialPlaylist()
    }

    fun loadInitialPlaylist() {
        viewModelScope.launch {
            loading.value = true
            try {
                // 從頻道資訊中取得 uploads 播放清單 id
                val channel = repository.getChannelInfo()
                val uploadsPlaylistId = channel.contentDetails.relatedPlaylists.uploads
                val response = repository.getPlaylistItems(
                    playlistId = uploadsPlaylistId,
                    maxResults = 30
                )
                _playlistItems.value = response.items
                _nextPageToken.value = response.nextPageToken
            } catch (e: Exception) {
                errorMsg.value = e.message
            } finally {
                loading.value = false
            }
        }
    }

    fun loadMorePlaylist() {
        viewModelScope.launch {
            if (_nextPageToken.value.isNullOrEmpty()) return@launch
            try {
                val channel = repository.getChannelInfo()
                val uploadsPlaylistId = channel.contentDetails.relatedPlaylists.uploads
                val response = repository.getPlaylistItems(
                    playlistId = uploadsPlaylistId,
                    maxResults = 20,
                    pageToken = _nextPageToken.value
                )
                _playlistItems.value = _playlistItems.value + response.items
                _nextPageToken.value = response.nextPageToken
            } catch (e: Exception) {
                errorMsg.value = e.message
            }
        }
    }
}