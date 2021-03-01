package com.example.otusapp.recipe.list.data

import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.base.utils.convertTo
import com.example.otusapp.base.utils.doIfSuccess
import com.example.otusapp.recipe.list.data.remote.RecipeListApi
import com.example.otusapp.recipe.list.data.remote.model.RecipeDto
import com.example.otusapp.recipe.list.data.remote.model.toDomain
import com.example.otusapp.recipe.list.domain.RecipeListRepository
import com.example.otusapp.recipe.list.domain.model.Recipe
import javax.inject.Inject

class RecipeListRepositoryImpl @Inject constructor(
    private val api: RecipeListApi
) : RecipeListRepository {

    private var offset = 0
    private val inMemoryCache = mutableListOf<RecipeDto>()

    override suspend fun loadRecipes(): Result<List<Recipe>> {
        var result = api.loadRecipes(offset, PAGE_SIZE)
        result.doIfSuccess {
            inMemoryCache.addAll(it.results)
            offset = inMemoryCache.size
            result = Result.Success(it.copy(results = inMemoryCache))
        }
        return result.convertTo { it.results.map(RecipeDto::toDomain) }
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}