package com.roger.skywatch

import com.roger.skywatch.data.api.response.CommentThreadsResponse
import com.roger.skywatch.data.api.response.PageInfo
import com.roger.skywatch.data.api.response.PlaylistResponse
import com.roger.skywatch.data.model.Comment
import com.roger.skywatch.data.model.CommentSnippet
import com.roger.skywatch.data.model.CommentThread
import com.roger.skywatch.data.model.CommentThreadSnippet
import com.roger.skywatch.data.model.PlaylistItem
import com.roger.skywatch.data.model.Video
import com.roger.skywatch.data.repository.IYoutubeRepository
import com.roger.skywatch.util.Constants
import kotlinx.coroutines.delay

open class FakeYoutubeRepository : IYoutubeRepository {
    override suspend fun getChannelInfo(): com.roger.skywatch.data.model.Channel {
        // 回傳一個假資料頻道
        return com.roger.skywatch.data.model.Channel(
            kind = "youtube#channel",
            etag = "etagChannel",
            id = Constants.CHANNEL_ID,
            snippet = com.roger.skywatch.data.model.Channel.Snippet(
                title = "Fake Channel",
                description = "Fake channel description",
                customUrl = "fakeChannel",
                publishedAt = "2020-01-01T00:00:00Z",
                thumbnails = mapOf(
                    "default" to com.roger.skywatch.data.model.Channel.Thumbnail(
                        url = "https://example.com/default.jpg", width = 88, height = 88
                    )
                ),
                defaultLanguage = "en",
                localized = com.roger.skywatch.data.model.Channel.LocalizedDescription(
                    title = "Fake Channel", description = "Fake channel description"
                ),
                country = "US"
            ),
            contentDetails = com.roger.skywatch.data.model.Channel.ContentDetails(
                relatedPlaylists = com.roger.skywatch.data.model.Channel.RelatedPlaylists(
                    likes = "FAKE_LIKES",
                    favorites = "FAKE_FAVORITES",
                    uploads = "FAKE_UPLOADS"
                )
            ),
            statistics = com.roger.skywatch.data.model.Channel.Statistics(
                viewCount = 100000L,
                subscriberCount = 5000L,
                hiddenSubscriberCount = false,
                videoCount = 200L
            ),
            topicDetails = null,
            status = com.roger.skywatch.data.model.Channel.Status(
                privacyStatus = "public",
                isLinked = true,
                longUploadsStatus = "unavailable",
                madeForKids = false,
                selfDeclaredMadeForKids = false
            ),
            brandingSettings = null,
            auditDetails = null,
            contentOwnerDetails = null,
            localizations = null
        )
    }

    override suspend fun getPlaylistItems(
        playlistId: String,
        maxResults: Int,
        pageToken: String?
    ): PlaylistResponse {
        delay(50)
        // 如果傳入 pageToken 為 "error"，則模擬發生例外
        if (pageToken == "error") {
            throw Exception("Simulated API error")
        }
        val items = List(maxResults) { index ->
            PlaylistItem(
                kind = "youtube#playlistItem",
                etag = "etag-$index",
                id = "playlistItem-$index",
                snippet = PlaylistItem.Snippet(
                    publishedAt = "2021-06-01T12:00:00Z",
                    channelId = Constants.CHANNEL_ID,
                    title = "Fake Video Title $index",
                    description = "Fake Video Description $index",
                    thumbnails = mapOf(
                        "medium" to PlaylistItem.Thumbnail(
                            url = "https://example.com/video_$index.jpg", width = 320, height = 180
                        )
                    ),
                    channelTitle = "Fake Channel",
                    videoOwnerChannelTitle = "Fake Owner",
                    videoOwnerChannelId = Constants.CHANNEL_ID,
                    playlistId = playlistId,
                    position = index,
                    resourceId = PlaylistItem.ResourceId(
                        kind = "youtube#video",
                        videoId = "video-$index"
                    )
                ),
                contentDetails = PlaylistItem.ContentDetails(
                    videoId = "video-$index",
                    startAt = "",
                    endAt = "",
                    note = "",
                    videoPublishedAt = "2021-06-01T12:00:00Z"
                ),
                status = PlaylistItem.Status(privacyStatus = "public")
            )
        }
        return PlaylistResponse(
            kind = "youtube#playlistItemListResponse",
            etag = "fakeEtag",
            nextPageToken = if (pageToken == null) "nextToken" else null,
            pageInfo = PageInfo(totalResults = 100, resultsPerPage = maxResults),
            items = items
        )
    }

    override suspend fun getVideoDetails(videoId: String): Video {
        delay(50)
        return Video(
            kind = "youtube#video",
            etag = "videoEtag",
            id = videoId,
            snippet = Video.Snippet(
                publishedAt = "2021-06-01T12:00:00Z",
                channelId = Constants.CHANNEL_ID,
                title = "Fake Video Title",
                description = "Fake Video Description",
                thumbnails = mapOf(
                    "medium" to Video.Thumbnail(
                        url = "https://example.com/video_detail.jpg", width = 320, height = 180
                    )
                ),
                channelTitle = "Fake Channel",
                tags = listOf("tag1", "tag2"),
                categoryId = "22",
                liveBroadcastContent = "none",
                defaultLanguage = "en",
                localized = Video.LocalizedDescription(
                    title = "Fake Video Title",
                    description = "Fake Video Description"
                ),
                defaultAudioLanguage = "en"
            ),
            contentDetails = Video.ContentDetails(
                duration = "PT5M30S",
                dimension = "2d",
                definition = "hd",
                caption = "false",
                licensedContent = true,
                regionRestriction = null,
                contentRating = null,
                projection = "rectangular",
                hasCustomThumbnail = false
            ),
            status = Video.Status(
                uploadStatus = "processed",
                failureReason = null,
                rejectionReason = null,
                privacyStatus = "public",
                publishAt = "2021-06-01T12:00:00Z",
                license = "youtube",
                embeddable = true,
                publicStatsViewable = true,
                madeForKids = false,
                selfDeclaredMadeForKids = false,
                containsSyntheticMedia = false
            ),
            statistics = Video.Statistics(
                viewCount = "10000",
                likeCount = "500",
                dislikeCount = "10",
                favoriteCount = "0",
                commentCount = "100"
            ),
            paidProductPlacementDetails = null,
            player = Video.Player(
                embedHtml = "<iframe></iframe>",
                embedHeight = 360,
                embedWidth = 640
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

    override suspend fun getVideoComments(
        videoId: String,
        maxResults: Int,
        pageToken: String?
    ) = run {
        delay(50)
        // 此處簡單模擬留言資料
        val items = List(maxResults) { index ->
            CommentThread(
                id = "commentThread-$index",
                snippet = CommentThreadSnippet(
                    videoId = videoId,
                    topLevelComment = Comment(
                        id = "comment-$index",
                        snippet = CommentSnippet(
                            authorDisplayName = "User $index",
                            authorProfileImageUrl = "https://example.com/avatar_$index.jpg",
                            textDisplay = "This is a comment number $index.",
                            likeCount = index,
                            publishedAt = "2021-06-01T12:00:00Z",
                            updatedAt = "2021-06-01T12:00:00Z"
                        )
                    ),
                    totalReplyCount = 0,
                    canReply = true,
                    isPublicThread = true
                )
            )
        }
        CommentThreadsResponse(
            kind = "youtube#commentThreadListResponse",
            etag = "commentEtag",
            nextPageToken = if (pageToken == null) "nextCommentToken" else null,
            pageInfo = CommentThreadsResponse.PageInfo(
                totalResults = 100,
                resultsPerPage = maxResults
            ),
            items = items
        )
    }
}