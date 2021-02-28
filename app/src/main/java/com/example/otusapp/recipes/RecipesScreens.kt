package com.example.otusapp.recipes

import android.os.Bundle
import com.example.otusapp.recipes.presentation.RecipesListFragment
import com.example.otusapp.recipiesdetail.RecipeDetailFragment
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