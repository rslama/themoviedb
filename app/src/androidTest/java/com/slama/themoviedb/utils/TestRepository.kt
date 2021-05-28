package com.slama.themoviedb.utils

import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import javax.inject.Inject


class TestRepository @Inject constructor(
    private val movieDetail: MovieDetail,
) : MovieRepository {
    override suspend fun getListOfMovies(page: Int): Result<List<MovieOverview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieDetail(movieOverview: MovieOverview): Result<MovieDetail> {

        return Result.Success(movieDetail)
    }

    override suspend fun getCollection(movieDetail: MovieDetail): Result<MovieDetail> {
        return Result.Success(movieDetail)
    }


}