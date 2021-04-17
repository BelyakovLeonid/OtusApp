package com.github.belyakovleonid.feature_recipe_list.data

import android.util.Log
import com.github.belyakovleonid.core.di.FragmentScope
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.core_network_api.model.convertTo
import com.github.belyakovleonid.core_network_api.model.doIfSuccess
import com.github.belyakovleonid.feature_recipe_list.data.remote.RecipeListApi
import com.github.belyakovleonid.feature_recipe_list.data.remote.model.RecipeDto
import com.github.belyakovleonid.feature_recipe_list.data.remote.model.toDomain
import com.github.belyakovleonid.feature_recipe_list.domain.RecipeListRepository
import com.github.belyakovleonid.feature_recipe_list.domain.model.Recipe
import javax.inject.Inject

@FragmentScope
class RecipeListRepositoryImpl @Inject constructor(
    private val api: RecipeListApi
) : RecipeListRepository {

    init {
        Log.d("MyTag", "RecipeListRepository hash = ${hashCode()}")
    }

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