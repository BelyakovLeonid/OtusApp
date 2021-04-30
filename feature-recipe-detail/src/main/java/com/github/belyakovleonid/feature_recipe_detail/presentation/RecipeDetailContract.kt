package com.github.belyakovleonid.feature_recipe_detail.presentation

import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_recipe_detail.presentation.model.RecipeDetailUi

object RecipeDetailContract {

    sealed class State(
        val isErrorVisible: Boolean,
        val isLoadingVisible: Boolean,
        val isContentVisible: Boolean
    ) : IState {
        object Loading : State(false, true, false)
        object Error : State(true, false, false)
        data class Data(val recipe: RecipeDetailUi) : State(false, false, true)
    }
}