package com.slama.remote

import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.requests.CollectionRequest
import com.slama.remote.requests.MovieDetailRequest
import com.slama.remote.requests.NowPlayingMoviesRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface MovieRepository {

    suspend fun getListOfMovies(page: Int = 1): Result<List<MovieOverview>>
    suspend fun getMovieDetail(movieOverview: MovieOverview): Result<MovieDetail>
    suspend fun getCollection(movieDetail: MovieDetail): Result<MovieDetail>

    class Impl(
        private val apiKey: String,
        private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : MovieRepository {


        override suspend fun getListOfMovies(page: Int): Result<List<MovieOverview>> {
            return NowPlayingMoviesRequest(
                RemoteService.endpoints(),
                dispatcher
            )
                .execute(apiKey, page)
        }

        override suspend fun getMovieDetail(movieOverview: MovieOverview): Result<MovieDetail> {

            return MovieDetailRequest(RemoteService.endpoints(), dispatcher)
                .execute(apiKey, movieOverview)
        }

        override suspend fun getCollection(movieDetail: MovieDetail): Result<MovieDetail> {
            return if (movieDetail.collectionId == MovieDetail.NOT_IN_COLLECTION) {
                Result.Success(movieDetail)
            } else {
                CollectionRequest(
                    RemoteService.endpoints(),
                    dispatcher
                )
                    .execute(apiKey, movieDetail.collectionId, movieDetail)
            }
        }
    }
}