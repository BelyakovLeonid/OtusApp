package com.example.otusapp.recipe.detail.domain

import com.example.otusapp.recipe.detail.domain.model.RecipeDetail
import com.github.belyakovleonid.core_network_api.model.Result
import javax.inject.Inject

class RecipeDetailInteractor @Inject constructor(
    private val recipesRepository: RecipeDetailRepository
) {

    suspend fun loadRecipe(id: Long): Result<RecipeDetail> {
        return recipesRepository.loadRecipe(id)
    }
}