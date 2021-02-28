package com.example.otusapp.recipe

import android.os.Bundle
import com.example.otusapp.recipe.detail.presentation.RecipeDetailFragment
import com.example.otusapp.recipe.list.presentation.RecipesListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object RecipesScreens {

    fun recipesList() = FragmentScreen {
        RecipesListFragment()
    }

    fun recipeDetail(recipeId: Long) = FragmentScreen {
        RecipeDetailFragment().apply {
            arguments = Bundle().apply {
                putLong(RecipeDetailFragment.ARG_RECIPE_ID, recipeId)
            }
        }
    }
}