package com.example.otusapp.recipe.list.domain

import com.example.otusapp.recipe.list.domain.model.Recipe
import com.github.belyakovleonid.core_network_api.model.Result

interface RecipeListRepository {
    suspend fun loadRecipes(): Result<List<Recipe>>
}