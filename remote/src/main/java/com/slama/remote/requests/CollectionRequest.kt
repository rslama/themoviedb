package com.slama.remote.requests

import com.slama.remote.RemoteService
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.remote.RemoteCollection
import com.slama.remote.data.remote.RemoteMovieOverview
import io.reactivex.rxjava3.core.Observable

internal class CollectionRequest(private val endpoints: RemoteService.Endpoints) {

    fun execute(
        apiKey: String,
        collectionId: Long,
        movieDetail: MovieDetail
    ): Observable<MovieDetail> {

        return endpoints
            .getCollection(collectionId, apiKey)
            .map { processResponse(it, movieDetail) }
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
