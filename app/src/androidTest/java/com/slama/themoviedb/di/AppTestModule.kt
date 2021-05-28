package com.slama.themoviedb.di

import android.content.Context
import android.content.Intent
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.themoviedb.detail.MovieDetailActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppTestModule {

    @Provides
    fun provideFakeMovieOverview() = MovieOverview(
        1,
        "original_title",
        "title",
        "overview",
        "backgroundPath",
        "posterPath",
        "releaseDate"
    )

    @Provides
    @Named("test_intent")
    fun provideMovieDetailIntent(
        @ApplicationContext context: Context,
        movieOverview: MovieOverview
    ): Intent {
        return MovieDetailActivity.createIntent(context, movieOverview)
    }

    @Provides
    fun provideCollection(movieOverview: MovieOverview): MovieDetail.Collection {

        return MovieDetail.Collection(
            "new_collection",
            "overview",
            "",
            "",
            listOf(movieOverview, movieOverview, movieOverview)
        )
    }

    @Provides
    fun provideMovieDetail(
        movieOverview: MovieOverview,
        collection: MovieDetail.Collection
    ): MovieDetail {

        return MovieDetail(
            1,
            1,
            "",
            emptyList(),
            1f,
            "1h 0mins",
            collection,
            movieOverview
        )
    }
}