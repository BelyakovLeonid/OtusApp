package com.github.belyakovleonid.core.starters

interface StartersProvider {

    fun provideRecipeDetailStarter(): RecipeDetailStarter

    fun provideRecipeListStarter(): RecipeListStarter
}