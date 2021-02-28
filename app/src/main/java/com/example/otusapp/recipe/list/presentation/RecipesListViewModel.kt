package com.example.otusapp.recipe.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otusapp.OtusApp
import com.example.otusapp.base.data.network.RetrofitClient
import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.base.presentation.IEvent
import com.example.otusapp.recipe.list.data.RecipesListRepositoryImpl
import com.example.otusapp.recipe.list.data.remote.RecipesListApi
import com.example.otusapp.recipe.list.domain.RecipesListInteractor
import com.example.otusapp.recipe.list.domain.model.Recipe
import com.example.otusapp.recipe.list.navigator.RecipesListNavigatorImpl
import com.example.otusapp.recipe.list.presentation.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.create

class RecipesListViewModel : ViewModel() {

    private val navigator = RecipesListNavigatorImpl(OtusApp.cicerone.router)
    private val recipesApi = RetrofitClient.getClient().create<RecipesListApi>()
    private val recipesRepo = RecipesListRepositoryImpl(recipesApi)
    private val recipesInteractor = RecipesListInteractor(recipesRepo)

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
            is RecipesListContract.Event.OnItemClickEvent -> navigator.openRecipeDetail(event.item.id)
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