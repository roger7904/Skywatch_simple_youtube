package com.roger.skywatch.data.repository

import com.roger.skywatch.data.api.response.PlaylistResponse
import com.roger.skywatch.data.model.Channel
import com.roger.skywatch.data.model.Video

interface IYoutubeRepository {

    // 取得播放清單，參數 playlistId 可從頻道資訊中獲得
    suspend fun getPlaylistItems(
        playlistId: String,
        maxResults: Int,
        pageToken: String? = null
    ): PlaylistResponse

    // 取得頻道資訊，頻道 id 從 Constants 中設定
    suspend fun getChannelInfo(): Channel

    // 取得影片詳細資訊，回傳 Video（依據 videos API 的完整資料）
    suspend fun getVideoDetails(videoId: String): Video
}