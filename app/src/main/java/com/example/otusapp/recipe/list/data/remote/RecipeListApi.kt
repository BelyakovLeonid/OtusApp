package com.example.otusapp.recipe.list.data.remote

import com.example.otusapp.recipe.list.data.remote.response.RecipesResponse
import com.github.belyakovleonid.core_network_api.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeListApi {

    @GET("recipes/complexSearch/")
    suspend fun loadRecipes(
        @Query("offset") offset: Int,
        @Query("number") number: Int = 10
    ): Result<RecipesResponse>
}