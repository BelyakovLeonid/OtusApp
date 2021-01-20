package com.example.otusapp.recipes.data.remote

import com.example.otusapp.recipes.data.remote.model.RecipesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "f96ceb4a0a5b4c1e9b0e70a2c5677fce"

interface RecipesApi {

    @GET("recipes/complexSearch/")
    suspend fun loadRecipes(
        @Query("apiKey") key: String = API_KEY,
        @Query("number") number: Int = 10
    ): RecipesResponse
}

object RetrofitClient {
    private var retrofit: RecipesApi? = null

    fun getClient(): RecipesApi {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipesApi::class.java)
        }

        return retrofit!!
    }
}