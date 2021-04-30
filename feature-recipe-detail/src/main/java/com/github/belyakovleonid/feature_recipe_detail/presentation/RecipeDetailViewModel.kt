package com.github.belyakovleonid.feature_recipe_detail.presentation

import androidx.lifecycle.viewModelScope
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core.viewmodel.AssistedVMFactory
import com.github.belyakovleonid.core_network_api.model.Result
import com.github.belyakovleonid.feature_recipe_detail.domain.RecipeDetailInteractor
import com.github.belyakovleonid.feature_recipe_detail.domain.model.RecipeDetail
import com.github.belyakovleonid.feature_recipe_detail.presentation.model.toUi
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class RecipeDetailViewModel @AssistedInject constructor(
    private val recipesInteractor: RecipeDetailInteractor,
    @Assisted private val params: RecipeDetailParams
) : BaseViewModel<RecipeDetailContract.State>(RecipeDetailContract.State.Loading) {

    init {
        viewModelScope.launch {
            val result = recipesInteractor.loadRecipe(params.recipeId)
            mutableState.value = transmitResultToState(result)
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

    @AssistedFactory
    interface Factory : AssistedVMFactory<RecipeDetailViewModel, RecipeDetailParams>
}