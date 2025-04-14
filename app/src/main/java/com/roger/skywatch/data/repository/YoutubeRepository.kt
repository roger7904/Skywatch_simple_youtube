package com.roger.skywatch.data.repository

import com.roger.skywatch.BuildConfig
import com.roger.skywatch.data.api.YoutubeApiService
import com.roger.skywatch.data.api.response.PlaylistResponse
import com.roger.skywatch.data.api.response.VideoDetailResponse
import com.roger.skywatch.data.model.Channel
import com.roger.skywatch.data.model.Video
import com.roger.skywatch.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YoutubeRepository @Inject constructor(
    private val apiService: YoutubeApiService
): IYoutubeRepository {
    private val apiKey = BuildConfig.YOUTUBE_API_KEY

    // 取得播放清單，參數 playlistId 可從頻道資訊中獲得
    override suspend fun getPlaylistItems(
        playlistId: String,
        maxResults: Int,
        pageToken: String?
    ): PlaylistResponse {
        return apiService.getPlaylistItems(
            playlistId = playlistId,
            maxResults = maxResults,
            pageToken = pageToken,
            apiKey = apiKey
        )
    }

    // 取得頻道資訊，頻道 id 從 Constants 中設定
    override suspend fun getChannelInfo(): Channel {
        val response = apiService.getChannelInfo(
            channelId = Constants.CHANNEL_ID,
            apiKey = apiKey
        )
        // 假設 response.items 非空，取第一個項目
        return response.items.first()
    }

    // 取得影片詳細資訊，回傳 Video（依據 videos API 的完整資料）
    override suspend fun getVideoDetails(videoId: String): Video {
        val response: VideoDetailResponse = apiService.getVideoDetails(
            videoId = videoId,
            apiKey = apiKey
        )
        // 假設 response.items 非空
        return response.items.first()
    }
}