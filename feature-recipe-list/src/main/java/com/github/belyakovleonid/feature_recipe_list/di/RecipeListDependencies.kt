package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.module_injector.BaseDependencies
import retrofit2.Retrofit
import javax.inject.Inject

class RecipeListDependencies @Inject constructor(
    val retrofit: Retrofit,
    val recipeDetailStarter: RecipeDetailStarter
) : BaseDependencies