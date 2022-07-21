package com.hackbyte.recibot.util

sealed class Resource<T>(
    val data: T? = null,
    val loading: Boolean = false,
    val throwable: Throwable? = null
) {

    class Success<T>(data: T?) : Resource<T>(data)

    class Loading<T>(loading: Boolean = false, data: T? = null) :
        Resource<T>(loading = loading, data = data)

    class Error<T>(throwable: Throwable, data: T? = null) :
        Resource<T>(throwable = throwable, data = data)

}
