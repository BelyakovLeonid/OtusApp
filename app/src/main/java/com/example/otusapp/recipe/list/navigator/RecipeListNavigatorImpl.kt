package com.example.otusapp.recipe.list.navigator

import com.example.otusapp.recipe.RecipesScreens
import com.example.otusapp.recipe.list.presentation.RecipeListNavigator
import com.github.terrakok.cicerone.Router

class RecipeListNavigatorImpl(
    private val router: Router
) : RecipeListNavigator {

    override fun openRecipeDetail(recipeId: Long) {
        router.navigateTo(RecipesScreens.recipeDetail(recipeId))
    }
}