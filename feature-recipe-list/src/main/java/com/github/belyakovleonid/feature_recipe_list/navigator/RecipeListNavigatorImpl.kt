package com.github.belyakovleonid.feature_recipe_list.navigator

import android.util.Log
import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListNavigator
import javax.inject.Inject

class RecipeListNavigatorImpl @Inject constructor(
    private val recipeDetailStarter: RecipeDetailStarter
) : RecipeListNavigator {

    init {
        Log.d("MyTag", "RecipeListNavigatorImpl hash = ${hashCode()}")
    }

    override fun openRecipeDetail(recipeId: Long) {
        recipeDetailStarter.start(recipeId)
    }
}