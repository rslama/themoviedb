package com.slama.themoviedb.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.utils.Schedulers
import com.slama.themoviedb.BuildConfig
import com.slama.themoviedb.R
import com.slama.themoviedb.detail.MovieDetailActivity
import com.slama.themoviedb.main.adapter.MainListAdapter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber

class MainListActivity : AppCompatActivity() {

    private lateinit var viewModel: MainListViewModel
    private lateinit var repository: MovieRepository
    private val disposables = CompositeDisposable()

    private val adapter by lazy {
        MainListAdapter(
            resources.getString(R.string.base_image_url)
        ) { movieOverview -> viewModel.openDetail(movieOverview) }
    }

    private val schedulers by lazy {
        Schedulers()
    }

    private val loadingView by lazy {
        findViewById<View>(R.id.main_list_progress_bar)
    }

    private val recyclerView by lazy {
        findViewById<RecyclerView>(R.id.main_list_recycler)
    }

    private val errorView by lazy {
        findViewById<TextView>(R.id.main_list_error)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_list)

        setupRecycler()

        repository = MovieRepository.Impl(BuildConfig.api_key)

        viewModel = ViewModelProvider(
            this,
            MainListViewModelProvider(repository, schedulers)
        )
            .get(MainListViewModel::class.java)

        disposables.add(
            viewModel
                .onCreate()
                .subscribe(this::render)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun render(state: MainListState) {

        loadingView.show(state.loadingView)
        recyclerView.show(state.dataView)
        errorView.show(state.errorView)

        when (state) {
            is MainListState.Success -> updateDataset(state.result)
            is MainListState.Fail -> renderError(state.error)
            is MainListState.Loading,
            is MainListState.DetailOpened -> { /*no-op*/
            }
            is MainListState.OpenDetail -> openMovieDetail(state.movieOverview)
        }
    }

    private fun openMovieDetail(movieOverview: MovieOverview) {
        startActivity(MovieDetailActivity.createIntent(this, movieOverview))
        viewModel.detailOpened()
    }

    private fun renderError(error: String) {
        errorView.text = error
    }

    private fun updateDataset(result: List<MovieOverview>) {
        Timber.d("Loaded state render")
        adapter.addItems(result)
    }

    private fun setupRecycler() {
        recyclerView.layoutManager =
            GridLayoutManager(this, resources.getInteger(R.integer.spanCount))
        recyclerView.adapter = adapter
    }

    private fun View.show(show: Boolean) {

        if (show) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.INVISIBLE
        }
    }
}