package com.example.otusapp.di

import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.feature_recipe_list.starter.RecipeListStarterImpl
import dagger.Binds
import dagger.Module

@Module
interface StartersModule {

    @Binds
    fun bindRecipeDetailStarter(starter: com.github.belyakovleonid.feature_recipe_detail.starter.RecipeDetailStarterImpl): RecipeDetailStarter

    @Binds
    fun bindRecipeListStarter(starter: RecipeListStarterImpl): RecipeListStarter
}