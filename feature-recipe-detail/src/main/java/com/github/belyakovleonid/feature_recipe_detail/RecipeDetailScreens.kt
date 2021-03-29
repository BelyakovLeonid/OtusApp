package com.github.belyakovleonid.feature_recipe_detail

import android.os.Bundle
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object RecipeDetailScreens {

    fun recipeDetail(recipeId: Long) = FragmentScreen {
        RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putLong(RecipeDetailFragment.ARG_RECIPE_ID, recipeId)
            }
        }
    }
}