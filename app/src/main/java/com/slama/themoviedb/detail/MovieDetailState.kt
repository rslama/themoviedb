package com.slama.themoviedb.detail

import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview

sealed class MovieDetailState {

    data class Init(
        val movieOverview: MovieOverview,
    ) : MovieDetailState()

    data class DetailLoaded(
        val movieDetail: MovieDetail,
    ) : MovieDetailState()
}