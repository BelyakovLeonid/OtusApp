package com.example.otusapp.di

import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.core.starters.StatisticsStarter
import com.github.belyakovleonid.feature_recipe_detail.starter.RecipeDetailStarterImpl
import com.github.belyakovleonid.feature_recipe_list.starter.RecipeListStarterImpl
import com.github.belyakovleonid.feature_statistics.starter.StatisticsStarterImpl
import dagger.Binds
import dagger.Module

@Module
interface StartersModule {

    @Binds
    fun bindRecipeDetailStarter(starter: RecipeDetailStarterImpl): RecipeDetailStarter

    @Binds
    fun bindRecipeListStarter(starter: RecipeListStarterImpl): RecipeListStarter

    @Binds
    fun bindStatisticsStarter(starter: StatisticsStarterImpl): StatisticsStarter
}