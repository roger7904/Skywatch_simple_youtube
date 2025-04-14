package com.roger.skywatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roger.skywatch.data.model.Channel
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

    // 新增頻道資訊 State
    private val _channelInfo = MutableStateFlow<Channel?>(null)
    val channelInfo: StateFlow<Channel?> = _channelInfo

    private val _nextPageToken = MutableStateFlow<String?>(null)
    val loading = MutableStateFlow(false)
    val errorMsg = MutableStateFlow<String?>(null)
    val searchQuery = MutableStateFlow("")

    init {
        loadChannelInfo()
        loadInitialPlaylist()
    }

    fun loadChannelInfo() {
        viewModelScope.launch {
            try {
                val channel = repository.getChannelInfo()
                _channelInfo.value = channel
                // cache?
            } catch (e: Exception) {
                errorMsg.value = e.message
            }
        }
    }

    fun loadInitialPlaylist() {
        viewModelScope.launch {
            loading.value = true
            try {
                val channel = _channelInfo.value ?: repository.getChannelInfo()
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
                val channel = _channelInfo.value ?: repository.getChannelInfo()
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