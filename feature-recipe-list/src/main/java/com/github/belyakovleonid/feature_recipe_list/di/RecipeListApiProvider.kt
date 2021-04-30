package com.github.belyakovleonid.feature_recipe_list.di

import com.github.belyakovleonid.feature_recipe_list.presentation.RecipeListViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface RecipeListApiProvider : BaseApiProvider {

    val viewModel: RecipeListViewModel
}