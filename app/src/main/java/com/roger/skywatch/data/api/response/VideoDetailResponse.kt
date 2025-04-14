package com.roger.skywatch.data.api.response

import com.roger.skywatch.data.model.Video

data class VideoDetailResponse(
    val kind: String,
    val etag: String,
    val pageInfo: PageInfo,
    val items: List<Video>
)