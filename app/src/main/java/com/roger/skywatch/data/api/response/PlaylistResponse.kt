package com.roger.skywatch.data.api.response

import com.roger.skywatch.data.model.PlaylistItem

data class PlaylistResponse(
    val kind: String,
    val etag: String,
    val nextPageToken: String?,
    val pageInfo: PageInfo,
    val items: List<PlaylistItem>
)

data class PageInfo(
    val totalResults: Int,
    val resultsPerPage: Int
)