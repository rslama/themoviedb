package com.slama.themoviedb.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.slama.remote.data.local.MovieOverview
import com.slama.themoviedb.R
import com.slama.themoviedb.detail.MovieDetailActivity
import com.slama.themoviedb.main.adapter.MainListAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.disposables.CompositeDisposable

@AndroidEntryPoint
class MainListActivity : AppCompatActivity() {

    private val viewModel: MainListViewModel by viewModels()
    private val disposables = CompositeDisposable()

    private val adapter by lazy {
        MainListAdapter(
            resources.getString(R.string.base_image_url)
        ) { movieOverview -> openMovieDetail(movieOverview) }
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
            is MainListState.Loading -> { /*no-op*/
            }
        }
    }

    private fun openMovieDetail(movieOverview: MovieOverview) {
        startActivity(MovieDetailActivity.createIntent(this, movieOverview))
    }

    private fun renderError(error: String) {
        errorView.text = error
    }

    private fun updateDataset(result: List<MovieOverview>) {
        adapter.addItems(result)
        //setup recycler here ensure a correct position when configuration changed
        setupRecycler()

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