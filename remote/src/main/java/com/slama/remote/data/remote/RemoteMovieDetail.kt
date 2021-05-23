package com.slama.remote.data.remote

import androidx.annotation.Keep

@Keep
internal data class RemoteMovieDetail(
    val id: Long,
    val belongs_to_collection: Collection,
    val homepage: String,
    val production_companies: List<RemoteProductionCompany>,
    val vote_average: Float,
    val runtime: Int
) {

    @Keep
    data class RemoteProductionCompany(
        val id: Long,
        val logo_path: String,
        val name: String,
        val origin_country: String
    )

    @Keep
    data class Collection(
        val id: Long,
        val name: String,
        val poster_path: String,
        val backdrop_path: String
    )
}