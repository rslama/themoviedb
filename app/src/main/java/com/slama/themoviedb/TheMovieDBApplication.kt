package com.slama.themoviedb

import android.app.Application
import timber.log.Timber

class TheMovieDBApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}