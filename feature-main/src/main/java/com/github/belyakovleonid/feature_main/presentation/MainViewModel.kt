package com.github.belyakovleonid.feature_main.presentation

import androidx.lifecycle.ViewModel
import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.starters.RecipeListStarter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val recipeListStarter: RecipeListStarter
) : ViewModel() {

    fun submitEvent(event: IEvent) {
        when (event) {
            is MainContract.Event.OnScreenOpenEvent -> recipeListStarter.startRecipeList()
        }
    }
}