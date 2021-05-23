package com.slama.remote

import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.requests.CollectionRequest
import com.slama.remote.requests.MovieDetailRequest
import com.slama.remote.requests.NowPlayingMoviesRequest
import io.reactivex.rxjava3.core.Observable

interface MovieRepository {

    fun getListOfMovies(page: Int = 1): Observable<Result<List<MovieOverview>>>
    fun getMovieDetail(movieOverview: MovieOverview): Observable<MovieDetail>
    fun getCollection(movieDetail: MovieDetail): Observable<MovieDetail>

    class Impl(private val apiKey: String) : MovieRepository {

        override fun getListOfMovies(page: Int): Observable<Result<List<MovieOverview>>> {

            return NowPlayingMoviesRequest(RemoteService.endpoints())
                .execute(apiKey, page)
        }

        override fun getMovieDetail(movieOverview: MovieOverview): Observable<MovieDetail> {

            return MovieDetailRequest(RemoteService.endpoints())
                .execute(apiKey, movieOverview)
        }

        override fun getCollection(movieDetail: MovieDetail): Observable<MovieDetail> {
            return if (movieDetail.collectionId == MovieDetail.NOT_IN_COLLECTION) {
                Observable.just(movieDetail)
            } else {
                CollectionRequest(RemoteService.endpoints())
                    .execute(apiKey, movieDetail.collectionId, movieDetail)
            }

        }
    }
}