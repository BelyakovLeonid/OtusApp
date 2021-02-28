package com.example.otusapp.recipes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otusapp.base.network.result.Result
import com.example.otusapp.recipes.data.RecipesRepositoryImpl
import com.example.otusapp.recipes.data.remote.RetrofitClient
import com.example.otusapp.recipes.domain.RecipesInteractor
import com.example.otusapp.recipes.domain.model.Recipe
import com.example.otusapp.recipes.presentation.model.RecipeUiModel
import com.example.otusapp.recipes.presentation.model.toUi
import kotlinx.coroutines.launch

class RecipesListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<RecipeUiModel>>(null)
    val items: LiveData<List<RecipeUiModel>> = _items

    //todo переписать после DI:
    private val recipesApi = RetrofitClient.getClient()
    private val recipesRepository = RecipesRepositoryImpl(recipesApi)
    private val recipesInteractor = RecipesInteractor(recipesRepository)

    init {
        loadMoreRecipes()
    }

    private fun loadMoreRecipes() {
        viewModelScope.launch {
            val result = recipesInteractor.loadMoreRecipes()
            _items.value = transmitResultToState(result)
        }
    }

    private fun transmitResultToState(result: Result<List<Recipe>>): List<RecipeUiModel> {
        return when (result) {
            is Result.Success -> result.value.map(Recipe::toUi)
            else -> emptyList()
        }
    }
}