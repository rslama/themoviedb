package com.slama.remote.data.remote

import androidx.annotation.Keep

@Keep
internal data class RemoteNowPlayingMovie(
    val id: Long,
    val original_title: String,
    val title: String,
    val overview: String,
    val adult: Boolean,
    val genres: List<Int>,
    val original_language: String,
    val backdrop_path: String,
    val poster_path: String,
    val popularity: Float,
    val release_date: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int
)