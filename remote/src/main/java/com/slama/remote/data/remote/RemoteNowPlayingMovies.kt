package com.slama.remote.data.remote

import androidx.annotation.Keep

@Keep
internal data class RemoteNowPlayingMovies(
    val dates: Dates,
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<RemoteMovieOverview>
) {
    @Keep
    data class Dates(
        val maximum: String,
        val minimum: String
    )
}