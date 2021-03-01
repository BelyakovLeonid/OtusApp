package com.example.otusapp.recipe.list.di

import com.example.otusapp.recipe.list.presentation.RecipeListFragment
import dagger.Subcomponent

@Subcomponent(modules = [RecipeListModule::class])
interface RecipeListComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RecipeListComponent
    }

    fun inject(fragment: RecipeListFragment)
}