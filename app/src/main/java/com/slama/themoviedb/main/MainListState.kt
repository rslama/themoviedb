package com.slama.themoviedb.main

import com.slama.remote.data.local.MovieOverview

sealed class MainListState {

    abstract val loadingView: Boolean
    abstract val dataView: Boolean
    abstract val errorView: Boolean

    data class Loading(
        override val loadingView: Boolean = true,
        override val dataView: Boolean = false,
        override val errorView: Boolean = false
    ) : MainListState()

    data class Success(
        val result: List<MovieOverview>,
        override val loadingView: Boolean = false,
        override val dataView: Boolean = true,
        override val errorView: Boolean = false
    ) : MainListState()

    data class Fail(
        val error: String,
        override val loadingView: Boolean = false,
        override val dataView: Boolean = false,
        override val errorView: Boolean = true
    ) : MainListState()
}