package com.slama.themoviedb.utils

import com.slama.themoviedb.util.SchedulersProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class TestSchedulers : SchedulersProvider {
    override fun ioScheduler(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun uiScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}