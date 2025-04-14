package com.roger.skywatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.roger.skywatch.data.model.Video
import com.roger.skywatch.ui.player.VideoPlayerScreen
import com.roger.skywatch.ui.playlist.PlaylistScreen
import com.roger.skywatch.ui.theme.SkywatchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class  MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            SkywatchTheme {
                // 用來切換畫面的狀態與保存選取的影片資料
                var currentScreen by remember { mutableStateOf("playlist") }
                var selectedVideo by remember { mutableStateOf<Video?>(null) }

                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        "playlist" -> {
                            PlaylistScreen(onVideoClick = { video ->
                                selectedVideo = video
                                currentScreen = "videoPlayer"
                            })
                        }
                        "videoPlayer" -> {
                            selectedVideo?.let { video ->
                                VideoPlayerScreen(videoInfo = video, onBack = {
                                    currentScreen = "playlist"
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}