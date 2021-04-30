package com.github.belyakovleonid.feature_recipe_detail.di

import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailParams
import com.github.belyakovleonid.feature_recipe_detail.presentation.RecipeDetailViewModel
import com.github.belyakovleonid.module_injector.BaseApiProvider

interface RecipeDetailApiProvider : BaseApiProvider {
    val viewModelFactory: AssistedVMFactory<RecipeDetailViewModel, RecipeDetailParams>
}