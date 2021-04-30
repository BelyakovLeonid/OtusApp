package com.github.belyakovleonid.core_network_api.model

fun <T, R> Result<T>.convertTo(convertation: (T) -> R): Result<R> {
    return when (this) {
        is Result.Success -> Result.Success(convertation(value))
        else -> this as Result<R>
    }
}

fun <T> Result<T>.doIfSuccess(action: (T) -> Unit) {
    if (this is Result.Success) action.invoke(value)
}