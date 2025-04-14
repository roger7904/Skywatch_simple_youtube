package com.roger.skywatch.data.api.response

import com.roger.skywatch.data.model.Channel

data class ChannelResponse(
    val kind: String,
    val etag: String,
    val pageInfo: PageInfo,
    val items: List<Channel>
)