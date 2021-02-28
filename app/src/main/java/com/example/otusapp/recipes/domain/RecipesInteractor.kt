package com.example.otusapp.recipes.domain

import com.example.otusapp.base.network.result.Result
import com.example.otusapp.recipes.domain.model.Recipe

class RecipesInteractor(
    private val recipesRepository: RecipesRepository
) {

    suspend fun loadMoreRecipes(): Result<List<Recipe>> {
        return recipesRepository.loadRecipes()
    }
}