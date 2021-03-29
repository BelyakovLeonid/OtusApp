package com.github.belyakovleonid.feature_recipe_list.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.feature_recipe_list.presentation.model.RecipeUi

object RecipeListContract {

    sealed class Event : IEvent {
        data class OnItemClickEvent(val item: RecipeUi) : Event()
        object OnScrolledToEnd : Event()
    }

    sealed class State(
        val isErrorVisible: Boolean,
        val isLoadingVisible: Boolean,
        val isContentVisible: Boolean
    ) : IState {
        object Loading : State(false, true, false)
        object Error : State(true, false, false)
        object NoElements : State(true, false, false)
        data class Data(val items: List<RecipeUi>) : State(false, false, true)
    }
}