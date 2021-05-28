package com.slama.themoviedb.detail

import androidx.lifecycle.ViewModel
import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.data.local.Result
import com.slama.themoviedb.main.MainListState
import com.slama.themoviedb.util.SchedulersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val schedulers: SchedulersProvider,
) : ViewModel() {

    private val publisher = BehaviorSubject.create<MovieDetailState>()

    internal fun onCreate(movieOverview: MovieOverview): Observable<MovieDetailState> {

        return publisher
            .compose(schedulers.observableTransformer())
            .also {
                publisher.onNext(MovieDetailState.Init(movieOverview = movieOverview))
                loadMovieDetail(movieOverview)
            }

    }

    private fun loadMovieDetail(movieOverview: MovieOverview) {

        CoroutineScope(Dispatchers.IO).launch {
            val movieDetailResult = repository
                .getMovieDetail(movieOverview)


            when (movieDetailResult) {
                is Result.GenericError -> MainListState.Fail("generic error: ${movieDetailResult.code}\n${movieDetailResult.error}")
                is Result.NetworkError -> MainListState.Fail("network error")
                is Result.Success -> {
                    val collectionResponse =
                        repository.getCollection(movieDetailResult.value)
                    processResult(collectionResponse)
                }
            }

        }
    }

    private fun processResult(result: Result<MovieDetail>) {
        when (result) {
            is Result.GenericError -> MainListState.Fail("generic error: ${result.code}\n${result.error}")
            is Result.NetworkError -> MainListState.Fail("network error")
            is Result.Success -> doOnSuccess(result.value)
        }
    }

    private fun doOnSuccess(movieDetail: MovieDetail) {

        //remove current movie from the collection
        val newMovieDetail = movieDetail.copy(
            collection = removeCurrentMovieFromCollection(
                movieDetail.id,
                movieDetail.collection
            )
        )
        publisher.onNext(MovieDetailState.DetailLoaded(newMovieDetail))

    }

    private fun removeCurrentMovieFromCollection(
        movieId: Long,
        collection: MovieDetail.Collection?
    ): MovieDetail.Collection? {

        return collection?.copy(movies = collection
            .movies
            .filter { movieOverview -> movieOverview.id != movieId })
            ?: collection

    }
}