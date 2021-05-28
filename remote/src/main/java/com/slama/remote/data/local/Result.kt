package com.slama.remote.data.local

sealed class Result<out T> {

    data class Success<out T>(
        val value: T, val currentPage: Int = 1,
        val totalPages: Int = 1,
    ) : Result<T>()

    data class GenericError(val code: Int? = null, val error: Throwable? = null) :
        Result<Nothing>()

    object NetworkError : Result<Nothing>()
}
