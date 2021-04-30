package com.github.belyakovleonid.feature_recipe_detail.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail

interface RecipeDetailRepository {
    suspend fun loadRecipe(id: Long?): Result<RecipeDetail>
}