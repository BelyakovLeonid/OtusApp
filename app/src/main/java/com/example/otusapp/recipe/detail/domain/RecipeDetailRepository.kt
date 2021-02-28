package com.example.otusapp.recipe.detail.domain

import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.recipe.detail.domain.model.RecipeDetail

interface RecipeDetailRepository {
    suspend fun loadRecipe(id: Long): Result<RecipeDetail>
}