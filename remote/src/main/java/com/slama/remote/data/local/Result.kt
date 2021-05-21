package com.slama.remote.data.local

data class Result<T>(
    val value: T,
    val currentPage: Int = 1,
    val totalPages: Int = 1,
    val isError: Boolean = false,
    val errorMessage: String? = null
)