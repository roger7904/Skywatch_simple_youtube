package com.roger.skywatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roger.skywatch.data.model.CommentThread
import com.roger.skywatch.data.repository.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoCommentsViewModel @Inject constructor(
    private val repository: YoutubeRepository
) : ViewModel() {

    private val _comments = MutableStateFlow<List<CommentThread>>(emptyList())
    val comments: StateFlow<List<CommentThread>> = _comments

    private val _nextPageToken = MutableStateFlow<String?>(null)
    val loading = MutableStateFlow(false)
    val errorMsg = MutableStateFlow<String?>(null)

    val hasMore: Boolean
        get() = !_nextPageToken.value.isNullOrEmpty()

    fun loadComments(videoId: String) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.getVideoComments(videoId = videoId)
                _comments.value = response.items
                _nextPageToken.value = response.nextPageToken
            } catch (e: Exception) {
                errorMsg.value = e.message
            } finally {
                loading.value = false
            }
        }
    }

    fun loadMoreComments(videoId: String) {
        viewModelScope.launch {
            if (_nextPageToken.value.isNullOrEmpty()) return@launch
            try {
                val response = repository.getVideoComments(
                    videoId = videoId,
                    pageToken = _nextPageToken.value
                )
                _comments.value = _comments.value + response.items
                _nextPageToken.value = response.nextPageToken
            } catch (e: Exception) {
                errorMsg.value = e.message
            }
        }
    }
}