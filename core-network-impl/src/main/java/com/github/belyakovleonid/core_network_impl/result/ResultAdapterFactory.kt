package com.github.belyakovleonid.core_network_impl.result

import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Just copied from https://github.com/androidbroadcast/AsyncResult
 **/
class ResultAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawReturnType: Class<*> = getRawType(returnType)
        if (rawReturnType == Call::class.java) {
            if (returnType is ParameterizedType) {
                val callInnerType: Type = getParameterUpperBound(0, returnType)
                if (getRawType(callInnerType) == com.github.belyakovleonid.core_network_api.model.Result::class.java) {
                    if (callInnerType is ParameterizedType) {
                        val resultInnerType = getParameterUpperBound(0, callInnerType)
                        return ResultCallAdapter<Any?>(resultInnerType)
                    }
                    return ResultCallAdapter<Nothing>(Nothing::class.java)
                }
            }
        }

        return null
    }
}

private class ResultCallAdapter<R>(private val type: Type) :
    CallAdapter<R, Call<com.github.belyakovleonid.core_network_api.model.Result<R>>> {

    override fun responseType() = type

    override fun adapt(call: Call<R>): Call<com.github.belyakovleonid.core_network_api.model.Result<R>> =
        ResultCall(call)
}