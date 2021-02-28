package com.example.otusapp.recipes.data.remote

import com.example.otusapp.base.constants.NetworkConstants
import com.example.otusapp.base.network.ApiKeyInterceptor
import com.example.otusapp.recipes.data.remote.model.RecipesResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipesApi {

    @GET("recipes/complexSearch/")
    suspend fun loadRecipes(
        @Query("number") number: Int = 10
    ): RecipesResponse
}

object RetrofitClient {
    private var retrofit: RecipesApi? = null

    fun getClient(): RecipesApi {
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
                .client(httpClient)
                .build()
                .create(RecipesApi::class.java)
        }

        return retrofit!!
    }
}