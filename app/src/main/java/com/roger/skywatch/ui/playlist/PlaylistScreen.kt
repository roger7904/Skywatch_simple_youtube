package com.roger.skywatch.ui.playlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roger.skywatch.data.model.PlaylistItem
import com.roger.skywatch.data.model.Video
import com.roger.skywatch.data.model.toVideo
import com.roger.skywatch.ui.components.VideoCard
import com.roger.skywatch.viewmodel.PlaylistViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * PlaylistScreen
 *
 * 從 PlaylistViewModel 取得播放清單 (List<PlaylistItem>)，
 * 依搜尋關鍵字過濾後轉換成 Video（透過擴充函數），
 * 並以 LazyColumn 列出。
 *
 * 點選影片會透過 onVideoClick 回傳 Video，供導航至 VideoPlayerScreen 使用。
 */
@Composable
fun PlaylistScreen(
    viewModel: PlaylistViewModel = hiltViewModel(),
    onVideoClick: (Video) -> Unit
) {
    val playlistItems by viewModel.playlistItems.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val channelInfo by viewModel.channelInfo.collectAsState()

    // 根據搜尋關鍵字進行過濾
    val filteredItems = if (searchQuery.isNotBlank()) {
        playlistItems.filter { it.snippet.title.contains(searchQuery, ignoreCase = true) }
    } else {
        playlistItems
    }

    // LazyColumn 狀態，觀察是否滑到底部以觸發載入更多
    val listState: LazyListState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .map { it ?: 0 }
            .distinctUntilChanged()
            .collect { lastIndex ->
                if (lastIndex >= filteredItems.size - 1 && !isLoading) {
                    viewModel.loadMorePlaylist()
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 搜尋欄位
        TextField(
            value = searchQuery,
            onValueChange = { viewModel.searchQuery.value = it },
            label = { Text("搜尋影片") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        errorMsg?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }

        if (isLoading && filteredItems.isEmpty()) {
            // 初次載入時顯示 Loading (可替換成 Skeleton 效果)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(8.dp))
                Text("載入中...", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp)
            ) {
                items(filteredItems) { item: PlaylistItem ->
                    // 利用擴充函數轉換成 Video（此函數請參照下方範例）
                    val video: Video = item.toVideo()
                    VideoCard(
                        video = video,
                        channelAvatarUrl = channelInfo?.snippet?.thumbnails?.get("default")?.url,
                        onClick = { onVideoClick(video) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                if (isLoading && filteredItems.isNotEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}