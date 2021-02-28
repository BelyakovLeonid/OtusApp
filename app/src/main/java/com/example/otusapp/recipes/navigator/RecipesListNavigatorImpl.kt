package com.example.otusapp.recipes.navigator

import com.example.otusapp.recipes.RecipesScreens
import com.example.otusapp.recipes.presentation.RecipesListNavigator
import com.github.terrakok.cicerone.Router

class RecipesListNavigatorImpl(
    private val router: Router
) : RecipesListNavigator {

    override fun openRecipeDetail(recipeId: Long) {
        router.navigateTo(RecipesScreens.recipeDetail(recipeId))
    }
}