package com.example.otusapp.recipe.detail.data.remote

import com.example.otusapp.base.network.result.Result
import com.example.otusapp.recipe.detail.data.remote.model.RecipeDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetailApi {

    @GET("recipes/{id}/information")
    suspend fun loadRecipeDetail(
        @Path("id") id: Long
    ): Result<RecipeDetailDto>
}