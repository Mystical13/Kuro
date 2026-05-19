package com.kurostream.tv.domain.model

data class Anime(
    val id: String,
    val title: String,
    val episodes: Int,
    val malId: Int?,
    val anilistId: Int?,
    val posterUrl: String,
    val rating: Float,
    val status: String
)
