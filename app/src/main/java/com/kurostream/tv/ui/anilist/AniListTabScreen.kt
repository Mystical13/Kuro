package com.kurostream.tv.ui.anilist

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Text

@Composable
fun AniListTabScreen(onSyncClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(32.dp)
    ) {
        Text("AniList Sync", color = Color.White, modifier = Modifier.padding(bottom = 24.dp))
        
        SyncButton(text = "Manual Sync", onClick = onSyncClick)

        LazyColumn(modifier = Modifier.weight(1f).padding(top = 24.dp)) {
            items(5) { index ->
                Box(modifier = Modifier.padding(8.dp).focusable().background(Color.DarkGray).padding(16.dp)) {
                    Text("Offline Cache Item $index", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SyncButton(text: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = Modifier
            .focusable(interactionSource = interactionSource)
            .background(if (isFocused) Color.White else Color.Gray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = if (isFocused) Color.Black else Color.White)
    }
}
