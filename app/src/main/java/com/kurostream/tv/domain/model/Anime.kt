package com.kurostream.tv.domain.model

data class Anime(
    val id: String,
    val title: String,
    val episodes: Int,
    val year: Int = 0,
    val season: Int = 1,
    val posterUrl: String,
    val backgroundUrl: String? = null,
    val description: String? = null,
    val rating: Float,
    val status: String
)
