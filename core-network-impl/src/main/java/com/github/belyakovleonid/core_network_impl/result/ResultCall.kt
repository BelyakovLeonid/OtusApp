package com.github.belyakovleonid.core_network_impl.result

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

/**
 * Just copied from https://github.com/androidbroadcast/AsyncResult
 **/
internal class ResultCall<T>(proxy: Call<T>) :
    CallDelegate<T, com.github.belyakovleonid.core_network_api.model.Result<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<com.github.belyakovleonid.core_network_api.model.Result<T>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): ResultCall<T> {
        return ResultCall(proxy.clone())
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<com.github.belyakovleonid.core_network_api.model.Result<T>>
    ) : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result = if (response.isSuccessful) {
                com.github.belyakovleonid.core_network_api.model.Result.Success(response.body() as T)
            } else {
                com.github.belyakovleonid.core_network_api.model.Result.Failure.HttpException(
                    response.code(),
                    response.message()
                )
            }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = when (error) {
                is HttpException -> com.github.belyakovleonid.core_network_api.model.Result.Failure.HttpException(
                    error.code(),
                    error.message()
                )
                else -> com.github.belyakovleonid.core_network_api.model.Result.Failure.Error(error)
            }

            callback.onResponse(proxy, Response.success(result))
        }
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }
}