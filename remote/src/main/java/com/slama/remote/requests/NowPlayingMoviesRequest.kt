package com.slama.remote.requests

import com.slama.remote.RemoteService
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.remote.RemoteNowPlayingMovies
import io.reactivex.rxjava3.core.Observable
import com.slama.remote.data.local.Result
import com.slama.remote.data.remote.RemoteNowPlayingMovie

internal class NowPlayingMoviesRequest(private val endpoints: RemoteService.Endpoints) {

    fun execute(apiKey: String, page: Int = 1): Observable<Result<List<MovieOverview>>> {
        return endpoints
            .getNowPlayingMovies(apiKey, page)
            .map { response -> convertResponse(response) }
            .onErrorReturn { throwable -> processError(throwable) }
    }

    private fun processError(throwable: Throwable): Result<List<MovieOverview>> {
        //TODO should be replaced with better error handling to return error state
        return Result(emptyList(), 0, 0, isError = true, throwable.message)
    }

    private fun convertResponse(response: RemoteNowPlayingMovies): Result<List<MovieOverview>> {

        return Result(
            response
                .results
                .map { remoteNowPlayingMovie -> convertSingle(remoteNowPlayingMovie) },
            response.page,
            response.total_pages,
            isError = false
        )

    }

    private fun convertSingle(remoteNowPlayingMovie: RemoteNowPlayingMovie): MovieOverview {
        return MovieOverview(
            remoteNowPlayingMovie.id,
            remoteNowPlayingMovie.original_title,
            remoteNowPlayingMovie.title,
            remoteNowPlayingMovie.overview,
            remoteNowPlayingMovie.backdrop_path,
            remoteNowPlayingMovie.poster_path,
            remoteNowPlayingMovie.release_date,
        )
    }


}