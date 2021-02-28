package com.example.otusapp.recipes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otusapp.base.network.result.Result
import com.example.otusapp.base.presentation.IEvent
import com.example.otusapp.recipes.data.RecipesRepositoryImpl
import com.example.otusapp.recipes.data.remote.RetrofitClient
import com.example.otusapp.recipes.domain.RecipesInteractor
import com.example.otusapp.recipes.domain.model.Recipe
import com.example.otusapp.recipes.presentation.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesListViewModel : ViewModel() {

    //todo переписать после DI:
    private val recipesApi = RetrofitClient.getClient()
    private val recipesRepository = RecipesRepositoryImpl(recipesApi)
    private val recipesInteractor = RecipesInteractor(recipesRepository)

    private val _items =
        MutableStateFlow<RecipesListContract.State>(RecipesListContract.State.Loading)
    val items: StateFlow<RecipesListContract.State> = _items

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
            is RecipesListContract.Event.OnItemClickEvent -> {
            } // TODO
            is RecipesListContract.Event.OnScrolledToEnd -> loadMoreRecipes()
        }
    }

    private fun transmitResultToState(result: Result<List<Recipe>>): RecipesListContract.State {
        return when (result) {
            is Result.Success -> {
                val recipes = result.value.map(Recipe::toUi)

                if (recipes.isNotEmpty()) {
                    RecipesListContract.State.Data(recipes)
                } else {
                    RecipesListContract.State.NoElements
                }
            }
            else -> {
                RecipesListContract.State.Error
            }
        }
    }
}