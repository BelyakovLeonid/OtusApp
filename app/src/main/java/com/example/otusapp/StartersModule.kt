package com.example.otusapp

import com.example.otusapp.recipe.RecipeDetailStarterImpl
import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.feature_recipe_list.starter.RecipeListStarterImpl
import dagger.Binds
import dagger.Module

@Module
interface StartersModule {

    @Binds
    fun bindRecipeDetailStarter(starter: RecipeDetailStarterImpl): RecipeDetailStarter

    @Binds
    fun bindRecipeListStarter(starter: RecipeListStarterImpl): RecipeListStarter
}