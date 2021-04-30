package com.github.belyakovleonid.feature_recipe_detail.data

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.core_network_api.model.convertTo
import com.github.belyakovleonid.feature_recipe_detail.data.remote.RecipeDetailApi
import com.github.belyakovleonid.feature_recipe_detail.data.remote.model.RecipeDetailDto
import com.github.belyakovleonid.feature_recipe_detail.data.remote.model.toDomain
import com.github.belyakovleonid.feature_recipe_detail.domain.RecipeDetailRepository
import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail
import javax.inject.Inject

class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipesApi: RecipeDetailApi
) : RecipeDetailRepository {

    override suspend fun loadRecipe(id: Long): Result<RecipeDetail> {
        val result = recipesApi.loadRecipeDetail(id)
        return result.convertTo(RecipeDetailDto::toDomain)
    }
}