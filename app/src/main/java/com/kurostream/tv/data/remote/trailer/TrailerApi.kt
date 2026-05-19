package com.kurostream.tv.data.remote.trailer

import retrofit2.http.GET
import retrofit2.http.Query

interface TrailerApi {
    @GET("search")
    suspend fun searchYouTubeTrailer(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("key") apiKey: String
    ): String // TODO: PHASE_3 Add data classes
}

interface TrailerExtension {
    suspend fun fetchCustomTrailerUrl(animeId: String): String?
}
