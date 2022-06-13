package com.danielsenik.mytv.service.util

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Ok<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null, throwable: Throwable) : Resource<T>(data, throwable)
}
