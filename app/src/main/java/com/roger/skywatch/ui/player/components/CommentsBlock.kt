package com.roger.skywatch.ui.player.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roger.skywatch.data.model.CommentThread
import com.roger.skywatch.viewmodel.VideoCommentsViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun CommentsBlock(
    videoId: String,
    viewModel: VideoCommentsViewModel = hiltViewModel()
) {
    // 初次載入留言
    LaunchedEffect(videoId) {
        viewModel.loadComments(videoId)
    }

    val comments by viewModel.comments.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .map { it ?: 0 }
            .distinctUntilChanged()
            .collect { lastIndex ->
                if (lastIndex >= comments.size - 1 && !isLoading) {
                    viewModel.loadMoreComments(videoId)
                }
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (errorMsg != null) {
            Text(
                text = errorMsg ?: "載入錯誤",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(8.dp)
            )
        }
        if (isLoading && comments.isEmpty()) {
            // 初次載入中顯示 Loading 指示
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(comments) { commentThread: CommentThread ->
                    CommentCard(commentThread = commentThread)
                    Divider(modifier = Modifier.padding(vertical = 4.dp))
                }

                item {
                    when {
                        isLoading && comments.isNotEmpty() -> {
                            Box(Modifier.fillMaxWidth().padding(16.dp), Alignment.Center) {
                                CircularProgressIndicator(strokeWidth = 2.dp)
                            }
                        }
                        !viewModel.hasMore -> {               // hasMore 見下一節
                            Text(
                                "已載入全部留言",
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}