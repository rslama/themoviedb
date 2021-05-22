package com.slama.themoviedb.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.slama.remote.MovieRepository
import com.slama.remote.utils.SchedulersProvider

class MainListViewModelProvider(
    private val repository: MovieRepository,
    private val schedulersProvider: SchedulersProvider
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainListViewModel(repository, schedulersProvider) as T
    }
}