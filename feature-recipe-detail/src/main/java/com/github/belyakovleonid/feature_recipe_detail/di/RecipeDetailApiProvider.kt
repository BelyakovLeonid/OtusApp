package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface RecipeDetailApiProvider : BaseApiProvider {
    val viewModel: RecipeDetailViewModel
}