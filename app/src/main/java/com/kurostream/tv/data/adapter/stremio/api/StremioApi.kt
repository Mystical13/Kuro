package com.kurostream.tv.data.adapter.stremio.api

import com.kurostream.tv.data.adapter.stremio.model.StremioCatalogResponse
import com.kurostream.tv.data.adapter.stremio.model.StremioStreamResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface StremioApi {
    @GET
    suspend fun getCatalog(@Url url: String): StremioCatalogResponse

    @GET
    suspend fun getStreams(@Url url: String): StremioStreamResponse
}
