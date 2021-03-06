package com.slama.themoviedb.di

import com.slama.remote.MovieRepository
import com.slama.remote.data.local.MovieDetail
import com.slama.remote.data.local.MovieOverview
import com.slama.remote.utils.SchedulersProvider
import com.slama.themoviedb.utils.TestRepository
import com.slama.themoviedb.utils.TestSchedulers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object TestViewModelProvider {

    @Provides
    @ViewModelScoped
    fun provideSchedulers(): SchedulersProvider = TestSchedulers()

    @Provides
    @ViewModelScoped
    fun provideRepository(movieDetail: MovieDetail): MovieRepository = TestRepository(movieDetail)
}