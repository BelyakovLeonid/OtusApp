package com.github.belyakovleonid.feature_recipe_detail.data.remote

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_detail.data.remote.model.RecipeDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetailApi {

    @GET("recipes/{id}/information")
    suspend fun loadRecipeDetail(
        @Path("id") id: Long
    ): Result<RecipeDetailDto>
}