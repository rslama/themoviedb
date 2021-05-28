package com.slama.remote.requests

import com.slama.remote.RemoteService
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.data.remote.RemoteCollection
import com.slama.remote.data.remote.RemoteMovieOverview
import kotlinx.coroutines.CoroutineDispatcher

internal class CollectionRequest(
    private val endpoints: RemoteService.Endpoints,
    private val dispatcher: CoroutineDispatcher
) : Request() {

    suspend fun execute(
        apiKey: String,
        collectionId: Long,
        movieDetail: MovieDetail
    ): Result<MovieDetail> {

        return saveApiCall(dispatcher) {
            processResponse(
                endpoints.getCollection(collectionId, apiKey),
                movieDetail
            )
        }


    }

    private fun processResponse(
        remoteCollection: RemoteCollection,
        movieDetail: MovieDetail
    ): MovieDetail {
        return movieDetail.copy(
            collection = processCollection(remoteCollection)
        )
    }

    private fun processCollection(remoteCollection: RemoteCollection): MovieDetail.Collection {
        return MovieDetail.Collection(
            remoteCollection.name,
            remoteCollection.overview,
            remoteCollection.backdrop_path,
            remoteCollection.poster_path,
            processMovies(remoteCollection.parts)
        )
    }

    private fun processMovies(movies: List<RemoteMovieOverview>): List<MovieOverview> {
        return movies
            .map { NowPlayingMoviesRequest.convertSingle(it) }
    }


}
