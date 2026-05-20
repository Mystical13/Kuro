package com.kurostream.tv.data.adapter.stremio

import com.kurostream.tv.data.adapter.stremio.api.StremioApi
import com.kurostream.tv.domain.model.Anime
import com.kurostream.tv.domain.model.StreamSource
import com.kurostream.tv.domain.provider.AnimeProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StremioAdapter @Inject constructor(
    private val api: StremioApi
) : AnimeProvider {
    override val name = "Stremio"

    // Default Kitsu anime addon for searching / browsing
    private val kitsuAddonUrl = "https://anime-kitsu.strem.fun/catalog/anime"

    override suspend fun getTrending(): List<Anime> = withContext(Dispatchers.IO) {
        try {
            val response = api.getCatalog("$kitsuAddonUrl/catalog/anime/kitsu-anime-trending.json")
            response.metas?.map { meta ->
                Anime(
                    id = meta.id,
                    title = meta.name ?: "Unknown",
                    episodes = 0,
                    year = meta.releaseInfo?.toIntOrNull() ?: 0,
                    season = 1,
                    posterUrl = meta.poster ?: "",
                    backgroundUrl = meta.background,
                    description = meta.description,
                    rating = meta.imdbRating?.toFloatOrNull() ?: 0f,
                    status = meta.status ?: "UNKNOWN"
                )
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun search(query: String): List<Anime> = withContext(Dispatchers.IO) {
        try {
            // Using search catalog for kitsu addon
            val response = api.getCatalog("$kitsuAddonUrl/catalog/anime/kitsu-anime-list/search=${query}.json")
            response.metas?.map { meta ->
                Anime(
                    id = meta.id,
                    title = meta.name ?: "Unknown",
                    episodes = 0,
                    year = meta.releaseInfo?.toIntOrNull() ?: 0,
                    season = 1,
                    posterUrl = meta.poster ?: "",
                    backgroundUrl = meta.background,
                    description = meta.description,
                    rating = meta.imdbRating?.toFloatOrNull() ?: 0f,
                    status = meta.status ?: "UNKNOWN"
                )
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getStreams(animeId: String, episodeNumber: Int): List<StreamSource> = withContext(Dispatchers.IO) {
        try {
            // Example using Torrentio for streams (commonly used stremio addon)
            val torrentioUrl = "https://torrentio.strem.fun/stream/anime"
            val response = api.getStreams("$torrentioUrl/${animeId}:${episodeNumber}.json")
            response.streams?.mapNotNull { stream ->
                if (stream.url != null) {
                    StreamSource(
                        url = stream.url,
                        quality = stream.title ?: stream.name ?: "Unknown",
                        provider = name,
                        isTorrent = false
                    )
                } else if (stream.infoHash != null) {
                    // Torrent streams
                    StreamSource(
                        url = "magnet:?xt=urn:btih:${stream.infoHash}",
                        quality = stream.title ?: stream.name ?: "Torrent",
                        provider = name,
                        isTorrent = true
                    )
                } else null
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
