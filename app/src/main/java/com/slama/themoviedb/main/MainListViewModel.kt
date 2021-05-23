package com.slama.themoviedb.main

import androidx.lifecycle.ViewModel
import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.remote.utils.SchedulersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject


@HiltViewModel
class MainListViewModel @Inject constructor(
    val repository: MovieRepository,
    val schedulers: SchedulersProvider,
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
        publish.onNext(state)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }


}