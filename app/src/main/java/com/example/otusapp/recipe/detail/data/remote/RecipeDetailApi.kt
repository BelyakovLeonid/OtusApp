package com.example.otusapp.recipe.detail.data.remote

import com.example.otusapp.recipe.detail.data.remote.model.RecipeDetailDto
import com.github.belyakovleonid.core_network_api.model.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetailApi {

    @GET("recipes/{id}/information")
    suspend fun loadRecipeDetail(
        @Path("id") id: Long
    ): Result<RecipeDetailDto>
}