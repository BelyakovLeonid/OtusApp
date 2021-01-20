package com.example.otusapp.recipes.data

import com.example.otusapp.recipes.data.remote.RecipesApi
import com.example.otusapp.recipes.data.remote.model.toDomain
import com.example.otusapp.recipes.domain.RecipesRepository
import com.example.otusapp.recipes.domain.model.Recipe

class RecipesRepositoryImpl(
    private val recipesApi: RecipesApi
) : RecipesRepository {

    override suspend fun loadRecipes(): List<Recipe> {
        return recipesApi.loadRecipes().results
            .map { it.toDomain() }
    }
}