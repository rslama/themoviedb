package com.slama.themoviedb.utils

import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class TestRepository @Inject constructor(
    private val movieDetail: MovieDetail,
) : MovieRepository {
    override fun getListOfMovies(page: Int): Observable<Result<List<MovieOverview>>> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetail(movieOverview: MovieOverview): Observable<MovieDetail> {

        return Observable.just(movieDetail)
    }

    override fun getCollection(movieDetail: MovieDetail): Observable<MovieDetail> {
        return Observable.just(movieDetail)
    }


}