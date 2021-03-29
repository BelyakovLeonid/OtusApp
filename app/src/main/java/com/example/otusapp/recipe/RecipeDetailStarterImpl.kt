package com.example.otusapp.recipe

import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RecipeDetailStarterImpl @Inject constructor(
    private val router: Router
) : RecipeDetailStarter {
    override fun start(recipeId: Long) {
        router.navigateTo(RecipesScreens.recipeDetail(recipeId))
    }
}