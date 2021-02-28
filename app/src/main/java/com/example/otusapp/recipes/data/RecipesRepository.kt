package com.example.otusapp.recipes.data

import com.example.otusapp.base.network.result.Result
import com.example.otusapp.base.utils.convertTo
import com.example.otusapp.base.utils.doIfSuccess
import com.example.otusapp.recipes.data.remote.RecipesApi
import com.example.otusapp.recipes.data.remote.model.RecipeDto
import com.example.otusapp.recipes.data.remote.model.toDomain
import com.example.otusapp.recipes.domain.RecipesRepository
import com.example.otusapp.recipes.domain.model.Recipe

class RecipesRepositoryImpl(
    private val recipesApi: RecipesApi
) : RecipesRepository {

    private var offset = 0
    private val inMemoryCache = mutableListOf<RecipeDto>()

    override suspend fun loadRecipes(): Result<List<Recipe>> {
        var result = recipesApi.loadRecipes(offset, PAGE_SIZE)
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