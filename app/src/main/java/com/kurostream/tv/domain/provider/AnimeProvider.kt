package com.kurostream.tv.domain.provider

import com.kurostream.tv.domain.model.Anime
import com.kurostream.tv.domain.model.StreamSource

interface AnimeProvider {
    val name: String
    
    suspend fun getTrending(): List<Anime>
    
    suspend fun search(query: String): List<Anime>
    
    suspend fun getStreams(animeId: String, episodeNumber: Int): List<StreamSource>
}
