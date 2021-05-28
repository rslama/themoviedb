package com.slama.themoviedb.di

import com.slama.remote.MovieRepository
import com.slama.themoviedb.BuildConfig
import com.slama.themoviedb.util.Schedulers
import com.slama.themoviedb.util.SchedulersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelProvider {

    @Provides
    @ViewModelScoped
    fun provideRepository(): MovieRepository = MovieRepository.Impl(BuildConfig.api_key)

    @Provides
    @ViewModelScoped
    fun provideSchedulers(): SchedulersProvider = Schedulers()

}