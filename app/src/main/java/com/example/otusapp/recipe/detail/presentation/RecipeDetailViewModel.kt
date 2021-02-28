package com.example.otusapp.recipe.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otusapp.base.data.network.RetrofitClient
import com.example.otusapp.base.data.network.result.Result
import com.example.otusapp.recipe.detail.data.RecipeDetailRepositoryImpl
import com.example.otusapp.recipe.detail.data.remote.RecipeDetailApi
import com.example.otusapp.recipe.detail.domain.RecipeDetailInteractor
import com.example.otusapp.recipe.detail.domain.model.RecipeDetail
import com.example.otusapp.recipe.detail.presentation.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.create

class RecipeDetailViewModel : ViewModel() {

    private val recipesApi = RetrofitClient.getClient().create<RecipeDetailApi>()
    private val recipesRepo = RecipeDetailRepositoryImpl(recipesApi)
    private val recipesInteractor = RecipeDetailInteractor(recipesRepo)

    private val _items =
        MutableStateFlow<RecipeDetailContract.State>(RecipeDetailContract.State.Loading)
    val items: StateFlow<RecipeDetailContract.State> = _items

    init {
        viewModelScope.launch {
            val recipeId = 0L
            val result = recipesInteractor.loadRecipe(recipeId)
            _items.value = transmitResultToState(result)
        }
    }

    private fun transmitResultToState(result: Result<RecipeDetail>): RecipeDetailContract.State {
        return when (result) {
            is Result.Success -> {
                RecipeDetailContract.State.Data(result.value.toUi())
            }
            else -> {
                RecipeDetailContract.State.Error
            }
        }
    }
}