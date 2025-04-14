package com.roger.skywatch.data.model

data class Video(
    val kind: String,
    val etag: String,
    val id: String,
    val snippet: Snippet,
    val contentDetails: ContentDetails,
    val status: Status,
    val statistics: Statistics,
    val paidProductPlacementDetails: PaidProductPlacementDetails?,
    val player: Player,
    val topicDetails: TopicDetails?,
    val recordingDetails: RecordingDetails?,
    val fileDetails: FileDetails?,
    val processingDetails: ProcessingDetails?,
    val suggestions: Suggestions?,
    val liveStreamingDetails: LiveStreamingDetails?,
    val localizations: Map<String, LocalizedDescription>?
){
    // snippet 層級，包含基本影片資訊
    data class Snippet(
        val publishedAt: String,           // ISO 格式 datetime
        val channelId: String,
        val title: String,
        val description: String,
        // thumbnails 為一個 Map，key 如 "default", "medium", "high" 等
        val thumbnails: Map<String, Thumbnail>,
        val channelTitle: String,
        val tags: List<String>?,
        val categoryId: String,
        val liveBroadcastContent: String,
        val defaultLanguage: String?,
        val localized: LocalizedDescription,
        val defaultAudioLanguage: String?
    )

    // 單一縮圖資訊
    data class Thumbnail(
        val url: String,
        val width: Int?,
        val height: Int?
    )

    // localized：本地化標題與描述資訊
    data class LocalizedDescription(
        val title: String,
        val description: String
    )

    // contentDetails 層級，包含影片播放相關資訊
    data class ContentDetails(
        val duration: String,              // ISO 8601 duration，例如 "PT15M33S"
        val dimension: String,             // 例如 "2d"
        val definition: String,            // "hd" 或 "sd"
        val caption: String,               // 例如 "true"/"false"
        val licensedContent: Boolean,
        val regionRestriction: RegionRestriction?,
        val contentRating: ContentRating?,
        val projection: String,
        val hasCustomThumbnail: Boolean
    )

    // 區域限制
    data class RegionRestriction(
        val allowed: List<String>?,
        val blocked: List<String>?
    )

    // 內容分級，各平台的評分資訊
    data class ContentRating(
        val acbRating: String?,
        val agcomRating: String?,
        val anatelRating: String?,
        val bbfcRating: String?,
        val bfvcRating: String?,
        val bmukkRating: String?,
        val catvRating: String?,
        val catvfrRating: String?,
        val cbfcRating: String?,
        val cccRating: String?,
        val cceRating: String?,
        val chfilmRating: String?,
        val chvrsRating: String?,
        val cicfRating: String?,
        val cnaRating: String?,
        val cncRating: String?,
        val csaRating: String?,
        val cscfRating: String?,
        val czfilmRating: String?,
        val djctqRating: String?,
        val djctqRatingReasons: List<String>?,
        val ecbmctRating: String?,
        val eefilmRating: String?,
        val egfilmRating: String?,
        val eirinRating: String?,
        val fcbmRating: String?,
        val fcoRating: String?,
        val fmocRating: String?,
        val fpbRating: String?,
        val fpbRatingReasons: List<String>?,
        val fskRating: String?,
        val grfilmRating: String?,
        val icaaRating: String?,
        val ifcoRating: String?,
        val ilfilmRating: String?,
        val incaaRating: String?,
        val kfcbRating: String?,
        val kijkwijzerRating: String?,
        val kmrbRating: String?,
        val lsfRating: String?,
        val mccaaRating: String?,
        val mccypRating: String?,
        val mcstRating: String?,
        val mdaRating: String?,
        val medietilsynetRating: String?,
        val mekuRating: String?,
        val mibacRating: String?,
        val mocRating: String?,
        val moctwRating: String?,
        val mpaaRating: String?,
        val mpaatRating: String?,
        val mtrcbRating: String?,
        val nbcRating: String?,
        val nbcplRating: String?,
        val nfrcRating: String?,
        val nfvcbRating: String?,
        val nkclvRating: String?,
        val oflcRating: String?,
        val pefilmRating: String?,
        val rcnofRating: String?,
        val resorteviolenciaRating: String?,
        val rtcRating: String?,
        val rteRating: String?,
        val russiaRating: String?,
        val skfilmRating: String?,
        val smaisRating: String?,
        val smsaRating: String?,
        val tvpgRating: String?,
        val ytRating: String?
    )

    // status 層級，影片上傳狀態與隱私相關資訊
    data class Status(
        val uploadStatus: String,
        val failureReason: String?,
        val rejectionReason: String?,
        val privacyStatus: String,
        val publishAt: String?,            // datetime
        val license: String,
        val embeddable: Boolean,
        val publicStatsViewable: Boolean,
        val madeForKids: Boolean,
        val selfDeclaredMadeForKids: Boolean,
        val containsSyntheticMedia: Boolean
    )

    // 統計資訊
    data class Statistics(
        val viewCount: String,
        val likeCount: String?,
        val dislikeCount: String?,
        val favoriteCount: String,
        val commentCount: String?
    )

    // 付費植入相關細節
    data class PaidProductPlacementDetails(
        val hasPaidProductPlacement: Boolean
    )

    // player 層級，嵌入影片資訊
    data class Player(
        val embedHtml: String,
        val embedHeight: Long,
        val embedWidth: Long
    )

    // topicDetails 層級，影片相關主題資訊
    data class TopicDetails(
        val topicIds: List<String>?,
        val relevantTopicIds: List<String>?,
        val topicCategories: List<String>?
    )

    // recordingDetails 層級，錄製日期資訊
    data class RecordingDetails(
        val recordingDate: String // datetime
    )

    // fileDetails 層級，檔案相關資訊
    data class FileDetails(
        val fileName: String,
        val fileSize: Long,
        val fileType: String,
        val container: String,
        val videoStreams: List<VideoStream>?,
        val audioStreams: List<AudioStream>?,
        val durationMs: Long,
        val bitrateBps: Long,
        val creationTime: String
    )

    // 單一影片串流資訊
    data class VideoStream(
        val widthPixels: Int,
        val heightPixels: Int,
        val frameRateFps: Double,
        val aspectRatio: Double,
        val codec: String,
        val bitrateBps: Long,
        val rotation: String,
        val vendor: String
    )

    // 單一音頻串流資訊
    data class AudioStream(
        val channelCount: Int,
        val codec: String,
        val bitrateBps: Long,
        val vendor: String
    )

    // processingDetails 層級，處理狀態與進度
    data class ProcessingDetails(
        val processingStatus: String,
        val processingProgress: ProcessingProgress,
        val processingFailureReason: String?,
        val fileDetailsAvailability: String,
        val processingIssuesAvailability: String,
        val tagSuggestionsAvailability: String,
        val editorSuggestionsAvailability: String,
        val thumbnailsAvailability: String
    )

    // 處理進度
    data class ProcessingProgress(
        val partsTotal: Long,
        val partsProcessed: Long,
        val timeLeftMs: Long
    )

    // suggestions 層級，提供建議資訊
    data class Suggestions(
        val processingErrors: List<String>?,
        val processingWarnings: List<String>?,
        val processingHints: List<String>?,
        val tagSuggestions: List<TagSuggestion>?,
        val editorSuggestions: List<String>?
    )

    // 單一標籤建議
    data class TagSuggestion(
        val tag: String,
        val categoryRestricts: List<String>
    )

    // liveStreamingDetails 層級，直播相關資訊
    data class LiveStreamingDetails(
        val actualStartTime: String?,
        val actualEndTime: String?,
        val scheduledStartTime: String?,
        val scheduledEndTime: String?,
        val concurrentViewers: Long?,
        val activeLiveChatId: String?
    )
}