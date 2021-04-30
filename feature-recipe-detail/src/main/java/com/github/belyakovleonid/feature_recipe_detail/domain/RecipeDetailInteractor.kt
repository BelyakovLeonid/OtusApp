package com.github.belyakovleonid.feature_recipe_detail.domain

import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail
import javax.inject.Inject

class RecipeDetailInteractor @Inject constructor(
    private val recipesRepository: RecipeDetailRepository
) {

    suspend fun loadRecipe(id: Long?): Result<RecipeDetail> {
        return recipesRepository.loadRecipe(id)
    }
}