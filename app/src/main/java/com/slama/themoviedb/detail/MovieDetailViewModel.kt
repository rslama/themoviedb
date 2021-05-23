package com.slama.themoviedb.detail

import androidx.lifecycle.ViewModel
import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.utils.SchedulersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    val repository: MovieRepository,
    val schedulers: SchedulersProvider,
) : ViewModel() {

    private val disposables = CompositeDisposable()
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
        disposables.add(
            repository
                .getMovieDetail(movieOverview)
                .flatMap { movieDetail ->
                    repository.getCollection(movieDetail)
                }
                .compose(schedulers.observableTransformer())
                .subscribe { doOnSuccess(it) }
        )
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

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}