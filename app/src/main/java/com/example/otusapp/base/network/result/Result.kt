package com.example.otusapp.base.network.result

sealed class Result<out T> {
    class Success<T>(val value: T) : Result<T>()

    sealed class Failure : Result<Nothing>() {
        class HttpException(val code: Int?, val message: String?) : Failure()
        class Error(e: Throwable) : Failure()
    }
}