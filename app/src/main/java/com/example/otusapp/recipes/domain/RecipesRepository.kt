package com.example.otusapp.recipes.domain

import com.example.otusapp.base.network.result.Result
import com.example.otusapp.recipes.domain.model.Recipe

interface RecipesRepository {
    suspend fun loadRecipes(): Result<List<Recipe>>
}