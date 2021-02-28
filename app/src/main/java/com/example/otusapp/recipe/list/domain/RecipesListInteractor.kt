package com.example.otusapp.recipe.list.domain

import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.recipe.list.domain.model.Recipe

class RecipesListInteractor(
    private val repository: RecipesListRepository
) {

    suspend fun loadMoreRecipes(): Result<List<Recipe>> {
        return repository.loadRecipes()
    }
}