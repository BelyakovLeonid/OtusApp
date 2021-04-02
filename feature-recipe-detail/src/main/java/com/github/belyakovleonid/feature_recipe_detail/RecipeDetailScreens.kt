package com.github.belyakovleonid.feature_recipe_detail

import com.github.belyakovleonid.core.presentation.withParams
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailFragment
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailParams
import com.github.terrakok.cicerone.androidx.FragmentScreen

object RecipeDetailScreens {

    fun recipeDetail(recipeId: Long) = FragmentScreen {
        RecipeDetailFragment().withParams(RecipeDetailParams(recipeId))
    }
}