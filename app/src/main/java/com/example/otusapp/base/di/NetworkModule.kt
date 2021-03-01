package com.example.otusapp.base.di

import com.example.otusapp.base.data.network.constants.NetworkConstants
import com.example.otusapp.base.data.network.interceptors.ApiKeyInterceptor
import com.example.otusapp.base.data.network.result.ResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
interface NetworkModule {

    @Binds
    @Named("ApiKeyInterceptor")
    fun bindsApiKeyInterceptor(interceptor: ApiKeyInterceptor): Interceptor

    companion object {

        @Provides
        @Singleton
        fun providesRetrofit(
            okHttpClient: OkHttpClient,
            converterFactory: Converter.Factory,
            callAdapterFactory: CallAdapter.Factory
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build()
        }

        @Provides
        fun providesHttpClient(
            @Named("ApiKeyInterceptor") apiKeyInterceptor: Interceptor,
            @Named("LoggingInterceptor") loggingInterceptor: Interceptor,
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()
        }

        @Provides
        @Named("LoggingInterceptor")
        fun providesLoggingInterceptor(): Interceptor = HttpLoggingInterceptor()

        @Provides
        fun providesConverterFactory(): Converter.Factory {
            return Json { ignoreUnknownKeys = true }
                .asConverterFactory("application/json".toMediaType())
        }

        @Provides
        fun providesCallAdapterFactory(): CallAdapter.Factory {
            return ResultAdapterFactory()
        }

        @Provides
        @Named("ApiKey")
        fun providesApiKey(): String {
            return NetworkConstants.API_KEY
        }
    }
}