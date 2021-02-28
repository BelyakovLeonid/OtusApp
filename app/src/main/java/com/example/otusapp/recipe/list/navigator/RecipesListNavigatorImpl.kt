package com.example.otusapp.recipe.list.navigator

import com.example.otusapp.recipe.RecipesScreens
import com.example.otusapp.recipe.list.presentation.RecipesListNavigator
import com.github.terrakok.cicerone.Router

class RecipesListNavigatorImpl(
    private val router: Router
) : RecipesListNavigator {

    override fun openRecipeDetail(recipeId: Long) {
        router.navigateTo(RecipesScreens.recipeDetail(recipeId))
    }
}