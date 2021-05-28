package com.slama.remote.requests

import com.slama.remote.RemoteService
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.data.remote.RemoteMovieOverview
import com.slama.remote.data.remote.RemoteNowPlayingMovies
import kotlinx.coroutines.CoroutineDispatcher

internal class NowPlayingMoviesRequest(
    private val endpoints: RemoteService.Endpoints,
    private val dispatcher: CoroutineDispatcher
) : Request() {

    suspend fun execute(apiKey: String, page: Int = 1): Result<List<MovieOverview>> {

        return saveApiCall(dispatcher) {
            convertResponse(
                endpoints
                    .getListOfPlayingMovies(apiKey, page)
            )
        }
    }

    private fun convertResponse(response: RemoteNowPlayingMovies): List<MovieOverview> {

        return response
            .results
            .map { remoteNowPlayingMovie -> convertSingle(remoteNowPlayingMovie) }
    }

    companion object {
        fun convertSingle(remoteMovieOverview: RemoteMovieOverview): MovieOverview {
            return MovieOverview(
                remoteMovieOverview.id,
                remoteMovieOverview.original_title,
                remoteMovieOverview.title,
                remoteMovieOverview.overview,
                remoteMovieOverview.backdrop_path,
                remoteMovieOverview.poster_path,
                remoteMovieOverview.release_date,
            )
        }

    }

}