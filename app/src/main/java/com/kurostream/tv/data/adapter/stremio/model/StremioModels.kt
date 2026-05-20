package com.kurostream.tv.data.adapter.stremio.model

import com.google.gson.annotations.SerializedName

data class StremioCatalogResponse(
    @SerializedName("metas") val metas: List<StremioMeta>
)

data class StremioMeta(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("poster") val poster: String?,
    @SerializedName("background") val background: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("imdbRating") val imdbRating: String?,
    @SerializedName("releaseInfo") val releaseInfo: String?,
    @SerializedName("status") val status: String?
)

data class StremioStreamResponse(
    @SerializedName("streams") val streams: List<StremioStream>
)

data class StremioStream(
    @SerializedName("name") val name: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("infoHash") val infoHash: String?,
    @SerializedName("fileIdx") val fileIdx: Int?
)
