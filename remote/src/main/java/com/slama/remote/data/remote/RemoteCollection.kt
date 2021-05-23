package com.slama.remote.data.remote

import androidx.annotation.Keep

@Keep
internal data class RemoteCollection(
    val id: Long,
    val name: String,
    val overview: String,
    val backdrop_path: String,
    val poster_path: String,
    val parts: List<RemoteMovieOverview>
)