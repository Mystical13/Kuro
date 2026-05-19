package com.kurostream.tv.domain.repository

import com.kurostream.tv.domain.model.Anime
import kotlinx.coroutines.flow.Flow

interface AnimeRepository {
    suspend fun getFeatured(): Flow<List<Anime>>
    // TODO: PHASE_2
}
