package com.example.otusapp.recipe.detail.presentation

import com.example.otusapp.base.presentation.IState
import com.example.otusapp.recipe.detail.presentation.model.RecipeDetailUi

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