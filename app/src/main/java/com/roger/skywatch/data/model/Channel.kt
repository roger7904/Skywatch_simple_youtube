package com.roger.skywatch.data.model

data class Channel(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails,
    val statistics: Statistics,
    val topicDetails: TopicDetails?,
    val status: Status,
    val brandingSettings: BrandingSettings?,
    val auditDetails: AuditDetails?,
    val contentOwnerDetails: ContentOwnerDetails?,
    // localizations 為多語系資訊，key 為語系代碼，例如 "en"、"zh-TW"。
    val localizations: Map<String, LocalizedDescription>?
){
    // snippet 部分，包含頻道基本資訊
    data class Snippet(
        val title: String,
        val description: String,
        val customUrl: String?,
        val publishedAt: String, // ISO 格式的 datetime
        // thumbnails 採用 Map 處理不同尺寸圖片，例如 "default", "medium", "high"
        val thumbnails: Map<String, Thumbnail>,
        val defaultLanguage: String?,
        val localized: LocalizedDescription,
        val country: String?
    )

    // 單一縮圖資訊
    data class Thumbnail(
        val url: String,
        val width: Int?,
        val height: Int?
    )

    // localized 語系化的標題與描述
    data class LocalizedDescription(
        val title: String,
        val description: String
    )

    // contentDetails 部分，主要為相關播放清單資訊
    data class ContentDetails(
        val relatedPlaylists: RelatedPlaylists
    )

    data class RelatedPlaylists(
        val likes: String,
        val favorites: String,
        val uploads: String
    )

    // statistics：頻道統計數據
    data class Statistics(
        val viewCount: Long,
        val subscriberCount: Long, // 注意此值會被四捨五入至三個有效數字
        val hiddenSubscriberCount: Boolean,
        val videoCount: Long
    )

    // topicDetails：包含主題相關資訊
    data class TopicDetails(
        val topicIds: List<String>?,
        val topicCategories: List<String>?
    )

    // status：頻道狀態
    data class Status(
        val privacyStatus: String,
        val isLinked: Boolean,
        val longUploadsStatus: String,
        val madeForKids: Boolean,
        val selfDeclaredMadeForKids: Boolean
    )

    // brandingSettings：頻道品牌化設定
    data class BrandingSettings(
        val channel: BrandingChannelSettings,
        val watch: BrandingWatchSettings
    )

    data class BrandingChannelSettings(
        val title: String,
        val description: String,
        val keywords: String?,
        val trackingAnalyticsAccountId: String?,
        val unsubscribedTrailer: String?,
        val defaultLanguage: String?,
        val country: String?
    )

    data class BrandingWatchSettings(
        val textColor: String?,
        val backgroundColor: String?,
        val featuredPlaylistId: String?
    )

    // auditDetails：審核細節
    data class AuditDetails(
        val overallGoodStanding: Boolean,
        val communityGuidelinesGoodStanding: Boolean,
        val copyrightStrikesGoodStanding: Boolean,
        val contentIdClaimsGoodStanding: Boolean
    )

    // contentOwnerDetails：內容擁有者資訊
    data class ContentOwnerDetails(
        val contentOwner: String,
        val timeLinked: String // datetime 格式
    )
}