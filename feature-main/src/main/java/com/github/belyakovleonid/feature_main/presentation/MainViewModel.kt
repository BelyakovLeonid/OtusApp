package com.github.belyakovleonid.feature_main.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.core.starters.StatisticsStarter
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val recipeListStarter: RecipeListStarter,
    private val statisticsStarter: StatisticsStarter
) : BaseViewModel<IState>() {

    override fun submitEvent(event: IEvent) {
        when (event) {
            is MainContract.Event.OnScreenOpenEvent -> statisticsStarter.start()
        }
    }
}