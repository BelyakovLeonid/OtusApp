package com.github.belyakovleonid.feature_recipe_list.navigator

import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListNavigator
import javax.inject.Inject

class RecipeListNavigatorImpl @Inject constructor(
    private val recipeDetailStarter: RecipeDetailStarter
) : RecipeListNavigator {

    override fun openRecipeDetail(recipeId: Long) {
        recipeDetailStarter.start(recipeId)
    }
}