package com.roger.skywatch.data.api

import com.roger.skywatch.data.api.response.ChannelResponse
import com.roger.skywatch.data.api.response.PlaylistResponse
import com.roger.skywatch.data.api.response.VideoDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApiService {

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String = "snippet,contentDetails",
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String? = null,
        @Query("key") apiKey: String
    ): PlaylistResponse

    @GET("channels")
    suspend fun getChannelInfo(
        @Query("part") part: String = "snippet,contentDetails,statistics",
        @Query("id") channelId: String,
        @Query("key") apiKey: String
    ): ChannelResponse

    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String = "snippet,contentDetails,statistics,player",
        @Query("id") videoId: String,
        @Query("key") apiKey: String
    ): VideoDetailResponse
}