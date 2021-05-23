package com.slama.themoviedb.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.themoviedb.R
import com.slama.themoviedb.util.ImageUtil
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import kotlin.math.roundToInt

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private val viewModel: MovieDetailViewModel by viewModels()

    private val detailImageView by lazy {
        findViewById<ImageView>(R.id.detail_movie_image)
    }

    private val detailTitle by lazy {
        findViewById<TextView>(R.id.detail_movie_title)
    }

    private val baseImageUrl by lazy {
        resources.getString(R.string.base_image_url)
    }

    private val movieOverviewView by lazy {
        findViewById<TextView>(R.id.detail_movie_overview)
    }
    private val playtimeView by lazy {
        findViewById<TextView>(R.id.detail_movie_playtime)
    }

    private val collectionMovieContainer by lazy {
        findViewById<LinearLayout>(R.id.detail_collection_container)
    }

    private val collectionGroup by lazy {
        findViewById<Group>(R.id.detail_collection_group)
    }

    private val collectionName by lazy {
        findViewById<TextView>(R.id.detail_collection_name)
    }

    private val collectionOverview by lazy {
        findViewById<TextView>(R.id.detail_collection_overview)
    }

    private var isRendered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_movie_detail)
        val movieOverview = intent.getParcelableExtra<MovieOverview>(MOVIE_OVERVIEW)!!
        setupActionBar(movieOverview)

        viewModel
            .onCreate(movieOverview)
            .subscribe(this::render)

    }

    private fun render(state: MovieDetailState) {
        Timber.d("state: $state")
        when (state) {
            is MovieDetailState.Init -> renderInitialView(state.movieOverview)
            is MovieDetailState.DetailLoaded -> renderLoadedDetail(state.movieDetail)
        }
    }

    private fun renderLoadedDetail(movieDetail: MovieDetail) {

        renderBase(
            movieDetail.movieOverview.backgroundImagePath,
            movieDetail.movieOverview.originalTitle,
            movieDetail.movieOverview.overview
        )
        playtimeView.text = String.format(getString(R.string.detail_playtime), movieDetail.runtime)

        renderCollection(movieDetail.collection)
    }

    private fun renderCollection(collection: MovieDetail.Collection?) {
        collection?.let {
            collectionGroup.visibility = View.VISIBLE
            collectionName.text = collection.name
            collectionOverview.text = collection.overview
            renderCollectionsMovies(collection.movies)

            return
        }
        collectionGroup.visibility = View.GONE
    }

    private fun renderCollectionsMovies(movies: List<MovieOverview>) {
        val inflater = LayoutInflater.from(this)
        val viewWidth = dpToPx(resources.getInteger(R.integer.detail_collection_view_width))
        val margin = dpToPx(resources.getInteger(R.integer.detail_collection_view_margin))
        movies
            .forEach { movieOverview ->
                val view: View = setupCollectionMovieView(
                    inflater,
                    movieOverview,
                    viewWidth,
                    margin
                )
                view.setOnClickListener {
                    startActivity(
                        createIntent(
                            this@MovieDetailActivity,
                            movieOverview
                        )
                    )
                }
                collectionMovieContainer.addView(view)
            }
    }

    private fun setupCollectionMovieView(
        inflater: LayoutInflater,
        movieOverview: MovieOverview,
        viewWidth: Int,
        margin: Int
    ): View {
        val view = inflater.inflate(R.layout.item_movie_overview, collectionMovieContainer, false)
        val layoutParams =
            FrameLayout.LayoutParams(viewWidth, FrameLayout.LayoutParams.WRAP_CONTENT)
        (layoutParams as ViewGroup.MarginLayoutParams).apply {

            marginEnd = margin
            marginStart = margin
        }
        view.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.color_movie_detail_collection_view_background
            )
        )

        view.layoutParams = layoutParams


        val imageView = view.findViewById<ImageView>(R.id.item_movie_image)
        ImageUtil.loadImageIntoView(
            baseImageUrl,
            movieOverview.backgroundImagePath,
            imageView,
            this
        )
        view
            .findViewById<TextView>(R.id.item_movie_title)
            .text = movieOverview.title
        view
            .findViewById<TextView>(R.id.item_movie_overview)
            .text = movieOverview.overview

        return view
    }

    private fun renderInitialView(movieOverview: MovieOverview) {

        renderBase(
            movieOverview.backgroundImagePath,
            movieOverview.originalTitle,
            movieOverview.overview
        )
    }

    private fun renderBase(
        backgroundImagePath: String?,
        originalTitle: String,
        overview: String
    ) {
        if (!isRendered) {
            ImageUtil.loadImageIntoView(
                baseImageUrl,
                backgroundImagePath,
                detailImageView,
                this@MovieDetailActivity
            )

            detailTitle.text = originalTitle
            movieOverviewView.text = overview
            isRendered = true
        }
    }

    private fun setupActionBar(movieOverview: MovieOverview) {
        val actionBar = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = movieOverview.originalTitle
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun dpToPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            resources.displayMetrics
        ).roundToInt()
    }

    companion object {

        const val MOVIE_OVERVIEW = "movie_overview"
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