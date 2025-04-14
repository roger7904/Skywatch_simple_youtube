package com.roger.skywatch.ui.player

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.roger.skywatch.data.model.Video
import com.roger.skywatch.viewmodel.VideoPlayerViewModel

@Composable
fun VideoPlayerScreen(
    videoInfo: Video,
    onBack: () -> Unit,
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {
    BackHandler {
        onBack()
    }

    // 根據影片 id 載入詳細資訊
    LaunchedEffect(videoInfo.id) {
        viewModel.loadVideo(videoInfo.id)
    }
    val videoDetail by viewModel.videoDetail.collectAsState()

    val context = LocalContext.current
    if (videoDetail == null) {
        // 若尚未取得資料，顯示 Loading
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
            Text("載入中...", style = MaterialTheme.typography.titleMedium)
        }
        return
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                // 建立 YouTubePlayerView 並加入 Lifecycle Observer
                YouTubePlayerView(ctx).apply {
                    // 將此 view 加入 lifecycle observer，確保播放器自動處理暫停、釋放等
                    if (ctx is ComponentActivity) {
                        ctx.lifecycle.addObserver(this)
                    }

                    addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            // 當播放器準備就緒時，自動載入並播放影片
                            youTubePlayer.loadVideo(videoDetail!!.id, 0f)
                        }
                    })
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = videoDetail!!.snippet.title, style = MaterialTheme.typography.titleSmall)
            Text(text = "頻道：${videoDetail!!.snippet.channelTitle}", style = MaterialTheme.typography.bodySmall)
            Text(
                text = "上傳時間：" +
                        videoDetail!!.snippet.publishedAt.substring(0, 10) + " " +
                        videoDetail!!.snippet.publishedAt.substring(11, 19),
                style = MaterialTheme.typography.labelSmall
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "影片描述", style = MaterialTheme.typography.titleMedium)
            Text(text = videoInfo.snippet.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "留言：\n(留言功能尚未實作)", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text("返回播放清單")
        }
    }
}