package com.slama.remote.requests

import com.slama.remote.RemoteService
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.data.remote.RemoteMovieDetail
import com.slama.remote.utils.TimeUtil
import kotlinx.coroutines.CoroutineDispatcher

internal class MovieDetailRequest(
    private val remoteService: RemoteService.Endpoints,
    private val dispatcher: CoroutineDispatcher
) : Request() {

    suspend fun execute(
        apiKey: String,
        movieOverview: MovieOverview
    ): Result<MovieDetail> {

        return saveApiCall(dispatcher) {
            processResponse(
                movieOverview,
                remoteService
                    .getMovieDetail(movieOverview.id, apiKey)
            )
        }

    }

    private fun processResponse(
        movieOverview: MovieOverview,
        remoteMovieDetail: RemoteMovieDetail
    ): MovieDetail {

        return MovieDetail(
            remoteMovieDetail.id,
            collectionId(remoteMovieDetail.belongs_to_collection),
            remoteMovieDetail.homepage,
            processProductionCompanies(remoteMovieDetail.production_companies),
            remoteMovieDetail.vote_average,
            TimeUtil.minsToHours(remoteMovieDetail.runtime),
            movieOverview = movieOverview
        )


    }

    private fun processProductionCompanies(productionCompanies: List<RemoteMovieDetail.RemoteProductionCompany>): List<MovieDetail.ProductionCompany> {
        return productionCompanies
            .map { MovieDetail.ProductionCompany(it.id, it.logo_path, it.name, it.origin_country) }
    }

    private fun collectionId(collection: RemoteMovieDetail.Collection?): Long {
        return collection?.id ?: MovieDetail.NOT_IN_COLLECTION
    }

}