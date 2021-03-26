package com.example.otusapp.recipe.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.base.presentation.IEvent
import com.example.otusapp.recipe.list.domain.RecipeListInteractor
import com.example.otusapp.recipe.list.domain.model.Recipe
import com.example.otusapp.recipe.list.presentation.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(
    private val navigator: RecipeListNavigator,
    private val recipesInteractor: RecipeListInteractor
) : ViewModel() {

    private val _items =
        MutableStateFlow<RecipeListContract.State>(RecipeListContract.State.Loading)
    val items: StateFlow<RecipeListContract.State> = _items

    init {
        loadMoreRecipes()
    }

    private fun loadMoreRecipes() {
        viewModelScope.launch {
            val result = recipesInteractor.loadMoreRecipes()
            _items.value = transmitResultToState(result)
        }
    }

    fun submitEvent(event: IEvent) {
        when (event) {
            is RecipeListContract.Event.OnItemClickEvent -> navigator.openRecipeDetail(event.item.id)
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