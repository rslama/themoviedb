package com.slama.themoviedb.di

import com.slama.remote.MovieRepository
import com.slama.remote.utils.Schedulers
import com.slama.remote.utils.SchedulersProvider
import com.slama.themoviedb.BuildConfig
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