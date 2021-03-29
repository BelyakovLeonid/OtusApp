package com.github.belyakovleonid.feature_recipe_list.starter

import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.feature_recipe_list.RecipeListScreens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RecipeListStarterImpl @Inject constructor(
    private val router: Router
) : RecipeListStarter {

    override fun startRecipeList() {
        router.navigateTo(RecipeListScreens.recipesList())
    }
}