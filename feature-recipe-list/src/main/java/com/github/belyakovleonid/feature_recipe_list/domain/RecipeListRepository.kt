package com.github.belyakovleonid.feature_recipe_list.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_list.domain.model.Recipe

interface RecipeListRepository {
    suspend fun loadRecipes(): Result<List<Recipe>>
}