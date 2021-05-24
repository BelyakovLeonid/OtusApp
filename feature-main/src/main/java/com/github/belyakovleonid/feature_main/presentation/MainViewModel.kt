package com.github.belyakovleonid.feature_main.presentation

import com.github.belyakovleonid.core.presentation.IEvent
import com.github.belyakovleonid.core.presentation.IState
import com.github.belyakovleonid.core.presentation.base.BaseViewModel
import com.github.belyakovleonid.core.starters.RecipeListStarter
import com.github.belyakovleonid.core.starters.StatisticsStarter
import com.github.belyakovleonid.core.starters.WeightTrackStarter
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val router: Router,
    recipeListStarter: RecipeListStarter,
    statisticsStarter: StatisticsStarter,
    weightTrackStarter: WeightTrackStarter
) : BaseViewModel<IState>() {

    private val tabScreens = listOf(
        recipeListStarter.getScreen(),
        statisticsStarter.getScreen(),
        weightTrackStarter.getScreen()
    )

    override fun submitEvent(event: IEvent) {
        when (event) {
            is MainContract.Event.OnScreenOpenEvent -> router.navigateTo(tabScreens[0])
            is MainContract.Event.OnTabSelectedEvent -> {
                router.replaceScreen(tabScreens[event.tabOrder])
            }
        }
    }
}