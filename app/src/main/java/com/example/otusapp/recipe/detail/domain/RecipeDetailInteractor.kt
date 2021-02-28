package com.example.otusapp.recipe.detail.domain

import com.example.otusapp.base.network.result.Result
import com.example.otusapp.recipe.detail.domain.model.RecipeDetail

class RecipeDetailInteractor(
    private val recipesRepository: RecipeDetailRepository
) {

    suspend fun loadRecipe(id: Long): Result<RecipeDetail> {
        return recipesRepository.loadRecipe(id)
    }
}