package com.slama.remote.data.local

data class MovieDetail(
    val id: Long,
    val collectionId: Long = NOT_IN_COLLECTION,
    val homepage: String,
    val productionCompanies: List<ProductionCompany>,
    val voteAverage: Float,
    val runtime: String,
    val collection: Collection? = null,
    val movieOverview: MovieOverview
) {

    data class Collection(
        val name: String,
        val overview: String,
        val backdropPath: String,
        val posterPath: String,
        val movies: List<MovieOverview>
    )

    data class ProductionCompany(
        val id: Long,
        val logoPathUrl: String?,
        val name: String,
        val originCountry: String
    )

    companion object {
        const val NOT_IN_COLLECTION = -1L
    }
}