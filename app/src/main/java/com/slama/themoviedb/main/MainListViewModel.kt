package com.slama.themoviedb.main

import androidx.lifecycle.ViewModel
import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.themoviedb.util.SchedulersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainListViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val schedulers: SchedulersProvider,
) : ViewModel() {

    private val publish = BehaviorSubject.create<MainListState>()

    init {
        publish.onNext(MainListState.Loading())
        loadMovies()
    }

    internal fun onCreate(): Observable<MainListState> {

        return publish
            .compose(schedulers.observableTransformer())
    }

    private fun loadMovies() {


        CoroutineScope(Dispatchers.IO).launch {
            val result = repository
                .getListOfMovies()

            publishState(result)
        }

    }

    private fun publishState(result: Result<List<MovieOverview>>) {
        val state: MainListState = when (result) {
            is Result.GenericError -> MainListState.Fail("generic error: ${result.code}\n${result.error}")
            is Result.NetworkError -> MainListState.Fail("network error")
            is Result.Success -> MainListState.Success(result.value)
        }
        publish.onNext(state)
    }
}