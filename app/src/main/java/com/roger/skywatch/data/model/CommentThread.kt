package com.roger.skywatch.data.model

data class CommentThread(
    val id: String,
    val snippet: CommentThreadSnippet
)

data class CommentThreadSnippet(
    val videoId: String,
    val topLevelComment: Comment,
    val totalReplyCount: Int,
    val canReply: Boolean,
    val isPublicThread: Boolean
)

data class Comment(
    val id: String,
    val snippet: CommentSnippet
)

data class CommentSnippet(
    val authorDisplayName: String,
    val authorProfileImageUrl: String,
    val textDisplay: String,
    val likeCount: Int,
    val publishedAt: String,  // ISO 格式字串
    val updatedAt: String     // ISO 格式字串
)