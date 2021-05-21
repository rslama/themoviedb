package com.slama.remote.requests

import com.slama.remote.RemoteService
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.remote.RemoteNowPlayingMovies
import io.reactivex.rxjava3.core.Observable
import com.slama.remote.data.local.Result

class NowPlayingMoviesRequest(private val endpoints: RemoteService.Endpoints) {

    fun execute(apiKey: String, page: Int = 1): Observable<Result<List<MovieOverview>>> {
        return endpoints
            .getNowPlayingMovies(apiKey, page)
            .map { response -> convertResponse(response) }
    }

    private fun convertResponse(response: RemoteNowPlayingMovies): Result<List<MovieOverview>> {

        return
    }


}