package com.example.otusapp.recipe.list.domain

import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.recipe.list.domain.model.Recipe

interface RecipesListRepository {
    suspend fun loadRecipes(): Result<List<Recipe>>
}