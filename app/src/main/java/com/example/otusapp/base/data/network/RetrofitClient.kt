package com.example.otusapp.base.data.network

import com.example.otusapp.base.data.network.constants.NetworkConstants
import com.example.otusapp.base.data.network.interceptors.ApiKeyInterceptor
import com.example.otusapp.base.data.network.result.ResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit {
        if (retrofit == null) {

            val converterFactory = Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType())

            val apiKeyInterceptor = ApiKeyInterceptor(NetworkConstants.API_KEY)
            val loggingInterceptor = HttpLoggingInterceptor()

            val httpClient = OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(ResultAdapterFactory())
                .client(httpClient)
                .build()
        }

        return retrofit!!
    }
}