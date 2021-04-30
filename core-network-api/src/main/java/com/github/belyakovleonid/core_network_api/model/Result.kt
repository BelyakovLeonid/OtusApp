package com.github.belyakovleonid.core_network_api.model

sealed class Result<out T> {
    class Success<T>(val value: T) : Result<T>()

    sealed class Failure : Result<Nothing>() {
        class HttpException(val code: Int?, val message: String?) : Failure()
        class Error(e: Throwable) : Failure()
    }
}