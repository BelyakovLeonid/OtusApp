package com.github.belyakovleonid.feature_recipe_list.starter

import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.feature_recipe_list.RecipeListScreens
import javax.inject.Inject

class RecipeListStarterImpl @Inject constructor() : RecipeListStarter {

    override fun getScreen() = RecipeListScreens.recipesList()
}