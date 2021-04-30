package com.github.belyakovleonid.feature_recipe_detail.starter

import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.feature_recipe_detail.RecipeDetailScreens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RecipeDetailStarterImpl @Inject constructor(
    private val router: Router
) : RecipeDetailStarter {
    override fun start(recipeId: Long) {
        router.navigateTo(RecipeDetailScreens.recipeDetail(recipeId))
    }
}