package com.example.otusapp.recipe.detail.di

import com.example.otusapp.recipe.detail.presentation.RecipeDetailFragment
import dagger.Subcomponent

@Subcomponent(modules = [RecipeDetailModule::class])
interface RecipeDetailComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RecipeDetailComponent
    }

    fun inject(fragment: RecipeDetailFragment)
}