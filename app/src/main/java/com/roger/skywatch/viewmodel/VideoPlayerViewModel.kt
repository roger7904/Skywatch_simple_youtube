package com.roger.skywatch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roger.skywatch.data.model.Video
import com.roger.skywatch.data.repository.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val repository: YoutubeRepository
) : ViewModel() {

    private val _videoDetail = MutableStateFlow<Video?>(null)
    val videoDetail: StateFlow<Video?> = _videoDetail

    fun loadVideo(videoId: String) {
        viewModelScope.launch {
            try {
                val video = repository.getVideoDetails(videoId)
                _videoDetail.value = video
            } catch (e: Exception) {
                // 可以進行錯誤處理或通知 UI
                _videoDetail.value = null
            }
        }
    }
}