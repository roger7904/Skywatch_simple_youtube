package com.roger.skywatch.ui.player.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.roger.skywatch.data.model.CommentThread

@Composable
fun CommentCard(
    commentThread: CommentThread
) {
    val snippet = commentThread.snippet.topLevelComment.snippet
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Image(
            painter = rememberAsyncImagePainter(model = snippet.authorProfileImageUrl),
            contentDescription = "留言者頭像",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = snippet.authorDisplayName, style = MaterialTheme.typography.titleSmall)
            Text(text = snippet.textDisplay, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = snippet.publishedAt.substring(0, 10) + " " + snippet.publishedAt.substring(11, 19),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}