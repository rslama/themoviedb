package com.slama.themoviedb.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class Schedulers : SchedulersProvider {
    override fun ioScheduler(): Scheduler {
        return Schedulers.io()
    }

    override fun uiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}