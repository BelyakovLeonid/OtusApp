package com.github.belyakovleonid.feature_recipe_list.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core.starters.RecipeDetailStarter
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_list.domain.RecipeListInteractor
import com.github.belyakovleonid.feature_recipe_list.domain.model.Recipe
import com.github.belyakovleonid.feature_recipe_list.presentation.model.toUi
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val recipeDetailStarter: RecipeDetailStarter,
    private val recipesInteractor: RecipeListInteractor
) : BaseViewModel<RecipeListContract.State>(RecipeListContract.State.Loading) {

    init {
        loadMoreRecipes()
    }

    private fun loadMoreRecipes() {
        viewModelScope.launch {
            val result = recipesInteractor.loadMoreRecipes()
            mutableState.value = transmitResultToState(result)
        }
    }

    override fun submitEvent(event: IEvent) {
        when (event) {
            is RecipeListContract.Event.OnItemClickEvent -> recipeDetailStarter.start(event.item.id)
            is RecipeListContract.Event.OnScrolledToEnd -> loadMoreRecipes()
        }
    }

    private fun transmitResultToState(result: Result<List<Recipe>>): RecipeListContract.State {
        return when (result) {
            is Result.Success -> {
                val recipes = result.value.map(Recipe::toUi)

                if (recipes.isNotEmpty()) {
                    RecipeListContract.State.Data(recipes)
                } else {
                    RecipeListContract.State.NoElements
                }
            }
            else -> {
                RecipeListContract.State.Error
            }
        }
    }
}