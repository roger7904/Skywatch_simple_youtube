package com.roger.skywatch.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.roger.skywatch.data.model.Video

@Composable
fun VideoCard(
    video: Video,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = video.snippet.thumbnails["medium"]?.url
                ),
                contentDescription = video.snippet.title,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                Text(
                    text = video.snippet.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = video.snippet.channelTitle,
                    style = MaterialTheme.typography.bodyMedium
                )
                // 假設 publishedAt 為 ISO 格式，可擷取日期與時間
                Text(
                    text = video.snippet.publishedAt.substring(0, 10) + " " + video.snippet.publishedAt.substring(11, 19),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}