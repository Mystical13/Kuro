package com.kurostream.tv.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey
    val animeId: String,
    val title: String,
    val imageUrl: String,
    val lastWatchedEpisode: Int,
    val lastWatchedPositionMs: Long,
    val timestamp: Long
)
