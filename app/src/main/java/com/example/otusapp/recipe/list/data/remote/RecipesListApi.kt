package com.example.otusapp.recipe.list.data.remote

import com.example.otusapp.base.network.result.Result
import com.example.otusapp.recipe.list.data.remote.response.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesListApi {

    @GET("recipes/complexSearch/")
    suspend fun loadRecipes(
        @Query("offset") offset: Int,
        @Query("number") number: Int = 10
    ): Result<RecipesResponse>
}