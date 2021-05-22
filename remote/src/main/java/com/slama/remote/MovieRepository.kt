package com.slama.remote

import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.requests.NowPlayingMoviesRequest
import io.reactivex.rxjava3.core.Observable

interface MovieRepository {

    fun getListOfMovies(page: Int = 1): Observable<Result<List<MovieOverview>>>

    class Impl(private val apiKey: String) : MovieRepository {

        override fun getListOfMovies(page: Int): Observable<Result<List<MovieOverview>>> {

            return NowPlayingMoviesRequest(RemoteService.getRemoteEndpoints())
                .execute(apiKey, page)
        }
    }
}