package com.example.otusapp.recipe.detail.domain

import com.example.otusapp.recipe.detail.domain.model.RecipeDetail
import com.github.belyakovleonid.core_network_api.model.Result

interface RecipeDetailRepository {
    suspend fun loadRecipe(id: Long): Result<RecipeDetail>
}