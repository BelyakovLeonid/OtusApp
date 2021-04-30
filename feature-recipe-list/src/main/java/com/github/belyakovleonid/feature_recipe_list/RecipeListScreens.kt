package com.github.belyakovleonid.feature_recipe_list

import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object RecipeListScreens {

    fun recipesList() = FragmentScreen {
        RecipeListFragment()
    }
}