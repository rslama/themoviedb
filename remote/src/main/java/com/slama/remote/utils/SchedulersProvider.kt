package com.slama.remote.utils

import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.Scheduler

interface SchedulersProvider {

    fun ioScheduler(): Scheduler
    fun uiScheduler(): Scheduler

    fun <T> observableTransformer(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream -> upstream
            .subscribeOn(ioScheduler())
            .observeOn(uiScheduler())
        }
    }
}