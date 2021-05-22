package com.slama.themoviedb.main

import androidx.lifecycle.ViewModel
import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.utils.SchedulersProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class MainListViewModel(
    private val repository: MovieRepository,
    private val schedulers: SchedulersProvider,
) : ViewModel() {

    private val publish = BehaviorSubject.create<MainListState>()
    private val disposables = CompositeDisposable()
    private var lastState: MainListState = MainListState.Loading()

    init {
        publish.onNext(MainListState.Loading())
        loadMovies()
    }

    internal fun onCreate(): Observable<MainListState> {

        return publish
            .compose(schedulers.observableTransformer())
    }

    internal fun openDetail(movieOverview: MovieOverview) {

        val newState = MainListState.OpenDetail(
            movieOverview,
            lastState.loadingView,
            lastState.dataView,
            lastState.errorView
        )

        publish.onNext(newState)
        lastState = newState

    }

    internal fun detailOpened() {

        val newState = MainListState.DetailOpened(
            lastState.loadingView,
            lastState.dataView,
            lastState.errorView
        )
        publish.onNext(newState)
        lastState = newState

    }

    private fun loadMovies() {

        repository
            .getListOfMovies()
            .compose(schedulers.observableTransformer())
            .subscribe { publishState(it) }

    }

    private fun publishState(result: Result<List<MovieOverview>>) {
        val state: MainListState = when (result.isError) {
            true -> MainListState.Fail(result.errorMessage ?: "")
            else -> MainListState.Success(result.value)
        }
        this.lastState = state
        publish.onNext(state)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


}