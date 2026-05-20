package com.kurostream.tv.domain.provider

import com.kurostream.tv.domain.model.StreamSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class ProviderAggregator(
    private val providers: List<AnimeProvider>
) {
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
