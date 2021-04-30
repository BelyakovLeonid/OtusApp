package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.module_injector.BaseDependencies
import retrofit2.Retrofit
import javax.inject.Inject

class RecipeDetailDependencies @Inject constructor(
    val retrofit: Retrofit
) : BaseDependencies