package com.example.otusapp.root.navigator

import com.example.otusapp.recipe.RecipesScreens
import com.example.otusapp.root.presentation.MainNavigator
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainNavigatorImpl @Inject constructor(
    private val router: Router
) : MainNavigator {

    override fun openRecipesList() {
        router.newRootScreen(RecipesScreens.recipesList())
    }
}