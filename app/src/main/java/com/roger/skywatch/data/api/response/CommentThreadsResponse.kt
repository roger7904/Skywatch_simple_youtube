package com.roger.skywatch.data.api.response

import com.roger.skywatch.data.model.CommentThread

// CommentThreadsResponse.kt
data class CommentThreadsResponse(
    val kind: String,
    val etag: String,
    val nextPageToken: String?,
    val pageInfo: PageInfo,
    val items: List<CommentThread>
){
    data class PageInfo(
        val totalResults: Int,
        val resultsPerPage: Int
    )
}