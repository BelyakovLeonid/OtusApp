package com.example.otusapp.recipe.list.domain

import com.example.otusapp.recipe.list.domain.model.Recipe
import com.github.belyakovleonid.core_network_api.model.Result
import javax.inject.Inject

class RecipeListInteractor @Inject constructor(
    private val repository: RecipeListRepository
) {

    suspend fun loadMoreRecipes(): Result<List<Recipe>> {
        return repository.loadRecipes()
    }
}