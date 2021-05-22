package com.slama.themoviedb.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.slama.remote.data.local.MovieOverview
import timber.log.Timber

class MovieDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("movieOverview: ${intent.getParcelableExtra<MovieOverview>(MOVIE_OVERVIEW)}")
    }

    companion object {

        private const val MOVIE_OVERVIEW = "movie_overview"
        fun createIntent(
            context: Context,
            movieOverview: MovieOverview
        ): Intent {

            return Intent(context, MovieDetailActivity::class.java)
                .apply {
                    putExtra(MOVIE_OVERVIEW, movieOverview)
                }
        }
    }
}