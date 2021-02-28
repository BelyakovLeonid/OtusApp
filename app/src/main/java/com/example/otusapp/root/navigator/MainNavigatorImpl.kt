package com.example.otusapp.root.navigator

import com.example.otusapp.recipes.RecipesScreens
import com.example.otusapp.root.presentation.MainNavigator
import com.github.terrakok.cicerone.Router

class MainNavigatorImpl(
    private val router: Router
) : MainNavigator {

    override fun openRecipesList() {
        router.newRootScreen(RecipesScreens.recipesList())
    }
}