package com.example.otusapp.recipes.domain

import com.example.otusapp.recipes.domain.model.Recipe

class RecipesInteractor(
    private val recipesRepository: RecipesRepository
) {

    suspend fun loadRecipes(): List<Recipe> {
        return recipesRepository.loadRecipes()
    }
}