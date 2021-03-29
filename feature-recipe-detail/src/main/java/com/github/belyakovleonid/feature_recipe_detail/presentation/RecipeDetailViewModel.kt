package com.github.belyakovleonid.feature_recipe_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_detail.domain.RecipeDetailInteractor
import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail
import com.github.belyakovleonid.feature_recipe_detail.presentation.model.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeDetailViewModel @Inject constructor(
    private val recipesInteractor: RecipeDetailInteractor
) : ViewModel() {

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