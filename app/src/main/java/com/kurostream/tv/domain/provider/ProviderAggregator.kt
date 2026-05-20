package com.kurostream.tv.domain.provider

import com.kurostream.tv.domain.model.Anime
import com.kurostream.tv.domain.model.StreamSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ProviderAggregator(
    private val providers: List<AnimeProvider>
) {
    suspend fun getTrending(): List<Anime> = coroutineScope {
        providers.map { provider ->
            async {
                try {
                    provider.getTrending()
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }.awaitAll().flatten().distinctBy { it.id }
    }

    suspend fun search(query: String): List<Anime> = coroutineScope {
        providers.map { provider ->
            async {
                try {
                    provider.search(query)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }.awaitAll().flatten().distinctBy { it.id }
    }

    suspend fun getStreams(animeId: String, episodeNumber: Int): List<StreamSource> = coroutineScope {
        providers.map { provider ->
            async {
                try {
                    provider.getStreams(animeId, episodeNumber)
                } catch (e: Exception) {
                    emptyList()
                }
            }
        }.awaitAll().flatten()
    }
}
