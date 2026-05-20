package com.kurostream.tv.domain.model

data class StreamSource(
    val url: String,
    val quality: String,
    val provider: String,
    val isTorrent: Boolean = false
)
