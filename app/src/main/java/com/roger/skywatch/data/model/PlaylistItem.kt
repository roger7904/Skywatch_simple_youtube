package com.roger.skywatch.data.model

// 整體播放清單項目結構
data class PlaylistItem(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails,
    val status: Status
){
    // snippet 內含影片基本資訊
    data class Snippet(
        val publishedAt: String,                // 發布日期 (ISO 格式)
        val channelId: String,
        val title: String,
        val description: String,
        // thumbnails 為一個 Map，key 代表縮圖尺寸（例如 "default", "medium", "high"）
        val thumbnails: Map<String, Thumbnail>,
        val channelTitle: String,
        val videoOwnerChannelTitle: String,
        val videoOwnerChannelId: String,
        val playlistId: String,
        val position: Int,                      // unsigned integer 對應 Int
        val resourceId: ResourceId
    )

    // 單一縮圖資訊
    data class Thumbnail(
        val url: String,
        val width: Int? = null,                   // 寬度為可選值
        val height: Int? = null                   // 高度為可選值
    )

    // resourceId 用來識別影片
    data class ResourceId(
        val kind: String,
        val videoId: String
    )

    // contentDetails 提供影片播放相關細節
    data class ContentDetails(
        val videoId: String,
        val startAt: String,
        val endAt: String,
        val note: String,
        val videoPublishedAt: String             // 發布時間 (ISO 格式)
    )

    // status 代表影片隱私狀態
    data class Status(
        val privacyStatus: String
    )
}

// 假設你的 PlaylistItem 及 Video 資料結構均已依照 API 文件設計完成
fun PlaylistItem.toVideo(): Video {
    return Video(
        kind = "youtube#video",
        etag = this.etag,
        id = this.snippet.resourceId.videoId,
        snippet = Video.Snippet(
            publishedAt = this.snippet.publishedAt,
            channelId = this.snippet.channelId,
            title = this.snippet.title,
            description = this.snippet.description,
            thumbnails = this.snippet.thumbnails.mapValues { (key, value) ->
                // 將 PlaylistItem.Thumbnail 轉換為 Video.Thumbnail
                Video.Thumbnail(
                    url = value.url,
                    width = value.width,
                    height = value.height
                )
            },
            channelTitle = this.snippet.channelTitle,
            tags = emptyList(),  // 目前無法從 PlaylistItem 取得 tags
            categoryId = "",
            liveBroadcastContent = "",
            defaultLanguage = null,
            localized = Video.LocalizedDescription(
                title = this.snippet.title,
                description = this.snippet.description
            ),
            defaultAudioLanguage = null
        ),
        contentDetails = Video.ContentDetails(
            duration = "PT0M0S", // 預設值；實際上可透過 videos API 取得正確 duration
            dimension = "2d",
            definition = "sd",
            caption = "false",
            licensedContent = false,
            regionRestriction = null,
            contentRating = null,
            projection = "",
            hasCustomThumbnail = false
        ),
        status = Video.Status(
            uploadStatus = "",
            failureReason = null,
            rejectionReason = null,
            privacyStatus = "public",
            publishAt = this.snippet.publishedAt,
            license = "youtube",
            embeddable = true,
            publicStatsViewable = true,
            madeForKids = false,
            selfDeclaredMadeForKids = false,
            containsSyntheticMedia = false
        ),
        statistics = Video.Statistics(
            viewCount = "0",
            likeCount = null,
            dislikeCount = null,
            favoriteCount = "0",
            commentCount = null
        ),
        paidProductPlacementDetails = null,
        player = Video.Player(
            embedHtml = "",
            embedHeight = 0,
            embedWidth = 0
        ),
        topicDetails = null,
        recordingDetails = null,
        fileDetails = null,
        processingDetails = null,
        suggestions = null,
        liveStreamingDetails = null,
        localizations = null
    )
}