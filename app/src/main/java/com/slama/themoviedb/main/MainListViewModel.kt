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

    init {
        publish.onNext(MainListState.Loading())
        loadMovies()
    }

    internal fun onCreate(): Observable<MainListState> {

        return publish
            .compose(schedulers.observableTransformer())
    }

    internal fun loadMovies() {

        repository
            .getListOfMovies()
            .compose(schedulers.observableTransformer())
            .subscribe { publishState(it) }

    }

    private fun publishState(result: Result<List<MovieOverview>>) {
        when (result.isError) {
            true -> publish.onNext(MainListState.Fail(result.errorMessage ?: ""))
            else -> publish.onNext(MainListState.Success(result.value))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}