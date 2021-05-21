package com.slama.remote.data.local

data class MovieOverview(
    val id: Long,
    val originalTitle: String,
    val title: String,
    val overview: String,
    val backgroundImagePath: String,
    val posterImagePath : String,
)