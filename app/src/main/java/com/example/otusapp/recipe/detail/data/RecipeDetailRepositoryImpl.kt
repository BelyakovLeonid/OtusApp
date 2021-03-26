package com.example.otusapp.recipe.detail.data

import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.base.utils.convertTo
import com.example.otusapp.recipe.detail.data.remote.RecipeDetailApi
import com.example.otusapp.recipe.detail.data.remote.model.RecipeDetailDto
import com.example.otusapp.recipe.detail.data.remote.model.toDomain
import com.example.otusapp.recipe.detail.domain.RecipeDetailRepository
import com.example.otusapp.recipe.detail.domain.model.RecipeDetail
import javax.inject.Inject

class RecipeDetailRepositoryImpl @Inject constructor(
    private val recipesApi: RecipeDetailApi
) : RecipeDetailRepository {

    override suspend fun loadRecipe(id: Long): Result<RecipeDetail> {
        val result = recipesApi.loadRecipeDetail(id)
        return result.convertTo(RecipeDetailDto::toDomain)
    }
}