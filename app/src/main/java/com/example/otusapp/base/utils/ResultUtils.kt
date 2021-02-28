package com.example.otusapp.base.utils

import com.example.otusapp.base.network.result.Result

fun <T, R> Result<T>.convertTo(convertation: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(convertation(value))
        else -> this as Result<R>
    }
}

fun <T> Result<T>.doIfSuccess(action: (T) -> Unit) {
    if (this is Result.Success) action.invoke(value)
}